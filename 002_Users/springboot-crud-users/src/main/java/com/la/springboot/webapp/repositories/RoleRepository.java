package com.la.springboot.webapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.la.springboot.webapp.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
    
}
