package com.la.springboot.webapp.servicio;

import java.util.List;
import java.util.stream.Collectors;

import com.la.springboot.webapp.dto.PublicacionDTO;
import com.la.springboot.webapp.dto.PublicacionRespuesta;
import com.la.springboot.webapp.excepciones.ResourceNotFoundException;
import com.la.springboot.webapp.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.la.springboot.webapp.entidades.Publicacion;

@Service //es un servicio
public class PublicacionServicioImpl implements PublicacionServicio{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		//cuando postman (JSON) envia los datos lo convertimos de dto a entidad
		Publicacion publicacion = mapearEntidad(publicacionDTO);
		
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		
		PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
		
		return publicacionRespuesta;
	}


	@Override
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagnia, int medidaDePagina, String ordenarPor, String sortDir){
		//se agregaron los parametros para la paginacion
		//sort dir para ordenar desce o asce
		//cambios paginacion
		System.out.println("numeroDePagnia: " + numeroDePagnia);
		System.out.println("medidaDePagina: " + medidaDePagina);
		System.out.println("ordenarPor: " + ordenarPor);
		System.out.println("sortDir: " + sortDir);
		//esta linea determina si debe ser asc o desc y por defecto esta en asc
		Sort sort;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(ordenarPor).descending();
		} else {
			sort = Sort.by(ordenarPor).ascending();
		}

		//aqui se crea el objeto de paginacion y que se procede a enviarse donde findAll hace todo el trabajo
		Pageable pageable = PageRequest.of(numeroDePagnia, medidaDePagina, sort);

		//obteniendo todas las publicaciones
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

		//obteniendo lista de publicaciones
		List<Publicacion> listaPublicaciones = publicaciones.getContent();
		
		//mapeando lista de publicaciones a lista de publicacionesDTO
		List<PublicacionDTO> contenido =  listaPublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
		
		//enviando datos de la clase de paginacion
		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());
		
		return publicacionRespuesta;
	}
	
	//convierte entidad (modelo) a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {

		//esto es lo que se mapea
		PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
		/*
		PublicacionDTO publicacionDTO = new PublicacionDTO();
		publicacionDTO.setId(publicacion.getId());
		publicacionDTO.setTitulo(publicacion.getTitulo());
		publicacionDTO.setDescripcion(publicacion.getDescripcion());
		publicacionDTO.setContenido(publicacion.getContenido());*/
		
		
		return publicacionDTO;
		
	}
	//convierte DTO a entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {

		//esto es lo que se mapea
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);

		/*Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());	*/
		
		
		return publicacion;
		
	}


	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Publicacion", "id", id) //expresion lambda
				);
		return mapearDTO(publicacion);
	}


	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Publicacion", "id", id) //expresion lambda
				);
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		
		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDTO(publicacionActualizada);
	}


	@Override
	public void eliminarPublicacion(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Publicacion", "id", id) //expresion lambda
				);
		
		publicacionRepositorio.delete(publicacion);
		
	}
	
	
}
