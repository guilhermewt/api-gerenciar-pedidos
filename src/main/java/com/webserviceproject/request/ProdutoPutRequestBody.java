package com.webserviceproject.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProdutoPutRequestBody {
	
	private long id;
	@NotEmpty(message = "the nome cannot be empty")
	private String nome;
	@NotEmpty(message = "the descricao cannot be empty")
	private String descricao;
	@NotNull(message = "the preco cannot be empty")
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
