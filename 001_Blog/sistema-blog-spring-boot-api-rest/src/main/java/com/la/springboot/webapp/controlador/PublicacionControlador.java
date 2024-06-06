package com.la.springboot.webapp.controlador;


import com.la.springboot.webapp.dto.PublicacionDTO;
import com.la.springboot.webapp.dto.PublicacionRespuesta;
import com.la.springboot.webapp.servicio.PublicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.la.springboot.webapp.utilerias.AppConstantes;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {

	@Autowired
	private PublicacionServicio publicacionServicio;

	@PreAuthorize("hasRole('ADMIN')") //solo los usuarios con rol admin pueden acceder a este metodo
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
		
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	//cambios por la paginacion
	public PublicacionRespuesta listarpublicaciones(
			@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
			@RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,//ordena por campo especifico
			@RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir)
	{ //se agrego @rEQUESTpARAM PARA LA PAGINACION
		return publicacionServicio.obtenerTodasLasPublicaciones(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}

	@PreAuthorize("hasRole('ADMIN')") //solo los usuarios con rol admin pueden acceder a este metodo
	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO, @PathVariable(name = "id") long id){
		PublicacionDTO publicionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicionRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')") //solo los usuarios con rol admin pueden acceder a este metodo
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
	}
}
