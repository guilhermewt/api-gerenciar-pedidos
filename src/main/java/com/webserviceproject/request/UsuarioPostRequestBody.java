package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.webserviceproject.entities.Pedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPostRequestBody {
	
	@NotEmpty(message = "the nome cannot be empty")
	private String nome;
	@NotEmpty(message = "the email cannot be empty")
	private String email;
	@NotEmpty(message = "the telefone nome cannot be empty")
	private String telefone;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	@NotEmpty(message = "the username cannot be empty")
	private String username;
	
	private Set<Pedido> pedido = new HashSet<>();
	
	public UsuarioPostRequestBody(String nome, String email, String telefone, String password,String username) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = password;
		this.username = username;
	}
}
