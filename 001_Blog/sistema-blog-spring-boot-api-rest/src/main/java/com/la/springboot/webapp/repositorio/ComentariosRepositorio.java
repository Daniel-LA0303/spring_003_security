package com.la.springboot.webapp.repositorio;

import com.la.springboot.webapp.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentariosRepositorio extends JpaRepository<Comentario, Long> {

    public List<Comentario> findByPublicacionId(Long publicacionId);
}
