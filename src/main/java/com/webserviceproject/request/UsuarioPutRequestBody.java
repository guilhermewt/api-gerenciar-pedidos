package com.webserviceproject.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPutRequestBody {
	
	private long id;
	private String nome;
	private String email;
	private String telefone;
	private String password;
	
	public UsuarioPutRequestBody(long id,String nome, String email, String telefone, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = senha;
	}
}
