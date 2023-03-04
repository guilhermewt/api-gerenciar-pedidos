package com.webserviceproject.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.config.JWTConfigurer.JWTAutenticationFilter;
import com.webserviceproject.data.JwtObject;
import com.webserviceproject.request.LoginGetRequestBody;

@RestController
public class authController {
	//consegui arrumar era so colocar um component agora estou vendo como padronizar os errors de tokein invalido, conseguir criar um novo nosuc e padronizar as resposta autenticacoes falsas
	
	@Autowired
	private JWTAutenticationFilter jwtAutenticationFilter;

	@PostMapping(value = "/login")
	public ResponseEntity<JwtObject> login(@RequestBody @Valid LoginGetRequestBody login) {
		return ResponseEntity.ok(jwtAutenticationFilter.attemptAuthentication(login));
	}
	
}
