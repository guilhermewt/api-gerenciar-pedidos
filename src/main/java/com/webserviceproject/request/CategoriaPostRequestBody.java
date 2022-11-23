package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import com.webserviceproject.entities.Produto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaPostRequestBody {
	
	private String nome;
	
	private Set<Produto> Produto = new HashSet<>();

	public CategoriaPostRequestBody(String nome) {
		super();
		this.nome = nome;
	}
}
