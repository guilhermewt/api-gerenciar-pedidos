package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import com.webserviceproject.entities.Produto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaPutRequestBody {
	
	private Long id;
	private String nome;
	
	private Set<Produto> Produto = new HashSet<>();

	public CategoriaPutRequestBody(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
}
