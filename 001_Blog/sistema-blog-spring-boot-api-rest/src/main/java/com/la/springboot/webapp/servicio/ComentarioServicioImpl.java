package com.la.springboot.webapp.servicio;

import com.la.springboot.webapp.dto.ComentarioDTO;
import com.la.springboot.webapp.entidades.Comentario;
import com.la.springboot.webapp.excepciones.BlogAppException;
import com.la.springboot.webapp.excepciones.ResourceNotFoundException;
import com.la.springboot.webapp.repositorio.ComentariosRepositorio;
import com.la.springboot.webapp.repositorio.PublicacionRepositorio;
import com.la.springboot.webapp.entidades.Publicacion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioServicioImpl implements ComentarioServicio{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentariosRepositorio comentariosRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;


    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        //buscamos la publicacion
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(
                () -> new ResourceNotFoundException("Publicacion ", "id", publicacionId));
        //asignamos la publicacion al comentario
        if (comentario != null) {
            comentario.setPublicacion(publicacion);
        }

        Comentario nuevoComentario = comentariosRepositorio.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {

        List<Comentario> comentarios = comentariosRepositorio.findByPublicacionId(publicacionId);

        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {

        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(
                () -> new ResourceNotFoundException("Publicacion ", "id", publicacionId));

        Comentario comentario = comentariosRepositorio.findById(comentarioId).orElseThrow(
                () -> new ResourceNotFoundException("Comentario ", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicituddeComentario) {

        //buscando publicacion y comenatario
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(
                () -> new ResourceNotFoundException("Publicacion ", "id", publicacionId));

        Comentario comentario = comentariosRepositorio.findById(comentarioId).orElseThrow(
                () -> new ResourceNotFoundException("Comentario ", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        comentario.setNombre(solicituddeComentario.getNombre());
        comentario.setEmail(solicituddeComentario.getEmail());
        comentario.setCuerpo(solicituddeComentario.getCuerpo());

        Comentario comentarioActualizado = comentariosRepositorio.save(comentario);

        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        //buscando publicacion y comenatario
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(
                () -> new ResourceNotFoundException("Publicacion ", "id", publicacionId));

        Comentario comentario = comentariosRepositorio.findById(comentarioId).orElseThrow(
                () -> new ResourceNotFoundException("Comentario ", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        comentariosRepositorio.delete(comentario);

    }


    private ComentarioDTO mapearDTO(Comentario comentario) {


        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        /*ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());*/

        return comentarioDTO;
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {

        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);

        /*Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());*/

        return comentario;
    }
}
