package com.la.springboot.webapp.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//nombre de la tabla y el campo titulo no debe repetirse
@Table(name = "publicaciones")
//no hay titulos repetidos
public class Publicacion {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "titulo", nullable = false, unique=true)
	private String titulo;
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	@Column(name = "contenido", nullable = false)
	private String contenido;

	//cascade y orphanRemoval es para cuando eliminamos una publicacion se eliminen los comentarios
	@JsonBackReference //corrije el error de recursividad
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comentario> comentarios = new HashSet<>();


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
	public Publicacion() {
		super();
	}
	public Publicacion(Long id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}
	
	
	
}
