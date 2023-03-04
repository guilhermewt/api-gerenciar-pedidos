package com.webserviceproject.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginGetRequestBody {
	@NotEmpty(message = "the username cannot be empty")
	private String username;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	
}
