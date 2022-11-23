package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import com.webserviceproject.entities.Pedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPostRequestBody {
	
	private String nome;
	private String email;
	private String telefone;
	private String password;
	
	private Set<Pedido> pedido = new HashSet<>();
	
	public UsuarioPostRequestBody(String nome, String email, String telefone, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = senha;
	}
}
