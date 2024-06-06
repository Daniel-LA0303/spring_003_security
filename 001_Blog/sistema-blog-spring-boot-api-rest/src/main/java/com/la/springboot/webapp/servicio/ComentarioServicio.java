package com.la.springboot.webapp.servicio;

import com.la.springboot.webapp.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioServicio {

    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicituddeComentario);

    public void eliminarComentario(Long publicacionId, Long comentarioId);

}
