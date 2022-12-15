package com.webserviceproject.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDomainPutRequestBody {
	
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	@NotEmpty(message = "the email cannot be empty")
	private String email;
	@NotEmpty(message = "the phone name cannot be empty")
	private String phone;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	@NotEmpty(message = "the username cannot be empty")
	private String username;
	
	public UserDomainPutRequestBody(String name, String email, String phone, String password, String username) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
}
