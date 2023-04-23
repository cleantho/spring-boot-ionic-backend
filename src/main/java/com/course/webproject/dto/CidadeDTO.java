package com.course.webproject.dto;

import java.io.Serializable;

import com.course.webproject.domain.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	public CidadeDTO() {
		super();
	}

	public CidadeDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public CidadeDTO(Cidade obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
