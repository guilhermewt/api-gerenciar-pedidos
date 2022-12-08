package com.webserviceproject.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPutRequestBody {
	
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
	
	public UsuarioPutRequestBody(String nome, String email, String telefone, String password, String username) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = password;
	}
}
