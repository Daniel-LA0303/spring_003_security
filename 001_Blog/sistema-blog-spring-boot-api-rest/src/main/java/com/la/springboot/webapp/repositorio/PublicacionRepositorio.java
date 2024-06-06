package com.la.springboot.webapp.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.la.springboot.webapp.entidades.Publicacion;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

}
