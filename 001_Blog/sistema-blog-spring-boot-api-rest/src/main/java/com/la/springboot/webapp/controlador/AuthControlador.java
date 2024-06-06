package com.la.springboot.webapp.controlador;


import com.la.springboot.webapp.dto.JWTAuthResponseDto;
import com.la.springboot.webapp.dto.LoginDto;
import com.la.springboot.webapp.repositorio.UsuarioRepositorio;
import com.la.springboot.webapp.seguridad.JwtTokenProvider;
import com.la.springboot.webapp.dto.RegistroDto;
import com.la.springboot.webapp.entidades.Role;
import com.la.springboot.webapp.entidades.Usuario;
import com.la.springboot.webapp.repositorio.RoleRepoditorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RoleRepoditorio roleRepoditorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //este metodo retorna un usuario si es que existe en la db
    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //obtenemos el token del jwt
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDto(token));
    }

    //este controller es para registrar un usuario
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDto registroDto){

        if (usuarioRepositorio.existsByUsername(registroDto.getUsername())){
            return new ResponseEntity<>("El nombre de usuario ya existe", org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepositorio.existsByEmail(registroDto.getEmail())){
            return new ResponseEntity<>("El email ya existe", org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDto.getNombre());
        usuario.setUsername(registroDto.getUsername());
        usuario.setEmail(registroDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDto.getPassword()));

        Role roles = roleRepoditorio.findByNombre("ROLE_USER").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepositorio.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente", org.springframework.http.HttpStatus.OK);
    }
}
