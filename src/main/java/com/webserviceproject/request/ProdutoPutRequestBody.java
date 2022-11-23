package com.webserviceproject.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProdutoPutRequestBody {
	
	private long id;
	private String nome;
	private String descricao;
	private Double preco;
	private String imgUrl;

	public ProdutoPutRequestBody(long id,String nome, String descricao, Double preco, String imgUrl) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.imgUrl = imgUrl;
	}
}
