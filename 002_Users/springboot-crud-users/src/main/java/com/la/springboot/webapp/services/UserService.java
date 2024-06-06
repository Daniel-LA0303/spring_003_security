package com.la.springboot.webapp.services;

import java.util.List;

import com.la.springboot.webapp.entities.User;

public interface UserService {
    
    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);
}
