package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.entities.ItemsDePedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProdutoPostRequestBody {
	
	private String nome;
	private String descricao;
	private Double preco;
	private String imgUrl;
	
	private Set<Categoria> categoria = new HashSet<>();
	
	private Set<ItemsDePedido> items = new HashSet<>();

	public ProdutoPostRequestBody(String nome, String descricao, Double preco, String imgUrl) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.imgUrl = imgUrl;
	}
}
