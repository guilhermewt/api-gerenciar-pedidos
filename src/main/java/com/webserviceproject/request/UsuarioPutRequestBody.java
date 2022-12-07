package com.webserviceproject.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPutRequestBody {
	
	private String nome;
	private String email;
	private String telefone;
	private String password;
	
	public UsuarioPutRequestBody(String nome, String email, String telefone, String password, String username) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = password;
	}
}
