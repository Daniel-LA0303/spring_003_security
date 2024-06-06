package com.la.springboot.webapp.seguridad;

import com.la.springboot.webapp.repositorio.UsuarioRepositorio;
import com.la.springboot.webapp.entidades.Role;
import com.la.springboot.webapp.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //metodo que carga el usuario de ladb
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username o email: " + usernameOrEmail));

        return new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }


}
