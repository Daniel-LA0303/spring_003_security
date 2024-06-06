package com.la.springboot.webapp.servicio;

import com.la.springboot.webapp.dto.PublicacionDTO;
import com.la.springboot.webapp.dto.PublicacionRespuesta;



public interface PublicacionServicio {

	//la mayorias enviara un objeto de tipo PublicacionDTO
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
	
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagnia, int medidaDePagina, String ordenarPor, String sortDir ); //se agregaron los parametros para la
	
	public PublicacionDTO obtenerPublicacionPorId(long id);
	
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
	
	public void eliminarPublicacion(long id);
}
