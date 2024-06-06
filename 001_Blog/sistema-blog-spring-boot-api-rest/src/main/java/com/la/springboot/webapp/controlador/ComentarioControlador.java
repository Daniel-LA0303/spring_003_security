package com.la.springboot.webapp.controlador;

import com.la.springboot.webapp.dto.ComentarioDTO;
import com.la.springboot.webapp.servicio.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> crearComentario(
            @PathVariable(name = "publicacionId") Long publicacionId,
            @Valid
            @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(@PathVariable(name = "publicacionId") Long publicacionId){
        return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(
            @PathVariable(name = "publicacionId") Long publicacionId,
            @PathVariable(name = "comentarioId") Long comentarioId){

        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(
            @PathVariable(name = "publicacionId") Long publicacionId,
            @PathVariable(name = "comentarioId") Long comentarioId,
            @Valid
            @RequestBody ComentarioDTO comentarioDTO){

        ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO);

        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(
            @PathVariable(name = "publicacionId") Long publicacionId,
            @PathVariable(name = "comentarioId") Long comentarioId){

        comentarioServicio.eliminarComentario(publicacionId, comentarioId);

        return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);
    }

}
