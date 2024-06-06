package com.la.springboot.webapp.repositorio;

import com.la.springboot.webapp.entidades.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepoditorio extends JpaRepository<Role, Long> {

    public Optional<Role> findByNombre(String nombre);
}
