package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_postagens")
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GERADOR DO ID
	private Long id; // Long != long, POIS SE TRATA DE UMA CLASSE QUE POSSUI MÉTODOS PARA SUA
						// MANIPULAÇÃO

	@NotBlank(message = "O campo deve ser preenchido") // NÃO ACEITA NULL E ESPAÇOS VAZIOS
	@Size(min = 5, max = 100, message = "O atributo título deve obter no mínimo 5 e no máximo 100 caracteres")
	private String titulo;

	@NotBlank(message = "O campo deve ser preenchido")
	@Size(min = 10, max = 1000, message = "O atributo texto deve obter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;

	@UpdateTimestamp // ATUALIZA A DATA E A HORA DE QUALQUER ALTERAÇÃO DO POST, DE ACORDO COM O
						// HORÁRIO DO SISTEMA
	private LocalDateTime data; // LOCALDATE SE TRATA DE UMA CLASSE QUE POSSUI MÉTODOS PARA SUA MANIPULAÇÃO
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}
