package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.webserviceproject.entities.Order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDomainPostRequestBody {
	
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
	
	private Set<Order> order = new HashSet<>();
	
	public UserDomainPostRequestBody(String name, String email, String phone, String password,String username) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.username = username;
	}
}
