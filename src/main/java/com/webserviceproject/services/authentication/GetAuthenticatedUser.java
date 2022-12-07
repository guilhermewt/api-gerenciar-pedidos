package com.webserviceproject.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.repository.UsuarioRepositorio;
import com.webserviceproject.services.exceptions.DataBaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedUser {
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	private final UsuarioRepositorio usuarioRepositorio;
	
	public Usuario userAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return usuarioRepositorio.findByUsername(authentication.getName()).orElseThrow(
				() -> new DataBaseException(authentication.getName()));
	}
}
