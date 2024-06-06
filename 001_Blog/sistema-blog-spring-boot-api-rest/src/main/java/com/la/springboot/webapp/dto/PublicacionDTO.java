package com.la.springboot.webapp.dto;

import com.la.springboot.webapp.entidades.Comentario;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class PublicacionDTO {

	private Long id;
	@NotEmpty
	@Size(min = 4, message = "El titulo debe tener al menos 4 caracteres")
	private String titulo;
	@NotEmpty
	@Size(min = 10, message = "La descripcion debe tener al menos 10 caracteres")
	private String descripcion;
	@NotEmpty
	private String contenido;

	private Set<Comentario> comentarios;

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public PublicacionDTO() {
		super();
	}
	
	
}
