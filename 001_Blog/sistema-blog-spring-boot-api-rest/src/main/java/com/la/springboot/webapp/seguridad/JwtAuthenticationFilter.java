package com.la.springboot.webapp.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ///obtenemos el token de la solicitus http
        String token = obtenerJWTdeLaSolicitud(request);

        //validamos el token
        if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)){
            //obtenemos el username del token
            String username = jwtTokenProvider.obtenerUsernameDelJWT(token);

            //obtenemos el usuario
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            //creamos una autenticacion
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //establecemo la seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response); //continuamos con la cadena de filtros

    }

    //Bearer token de acceso
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length()); //retornamos el token sin la palabra Bearer" "
        }

        return null;
    }
}
