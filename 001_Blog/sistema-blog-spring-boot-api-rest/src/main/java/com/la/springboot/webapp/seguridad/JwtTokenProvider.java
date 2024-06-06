package com.la.springboot.webapp.seguridad;


import com.la.springboot.webapp.excepciones.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }


    public String obtenerUsernameDelJWT(String token) {

        //obtenermos los datos encriptados del token
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token invalido");
        }catch (MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token invalido");
        }catch (ExpiredJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token expirado");
        }catch (UnsupportedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token no soportado");
        }catch (IllegalArgumentException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token vacio");
        }

    }
}
