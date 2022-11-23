package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.request.UsuarioPostRequestBody;
import com.webserviceproject.request.UsuarioPutRequestBody;
import com.webserviceproject.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
	private final UsuarioService service;
	
	@RequestMapping(value = "/all")
	public ResponseEntity<List<Usuario>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findById(@PathVariable long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Usuario> insert(@RequestBody UsuarioPostRequestBody usuarioPostRequestBody){
		return new ResponseEntity<Usuario>(service.insert(usuarioPostRequestBody), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> update(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody){
		service.atualizarUsuario(usuarioPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
