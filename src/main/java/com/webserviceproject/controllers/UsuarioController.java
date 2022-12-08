package com.webserviceproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping(value = "/admin/all/")
	public ResponseEntity<List<Usuario>> findAllNonPageable(){
		return ResponseEntity.ok(service.findAllNonPageable());
	}
	
	@GetMapping(value = "/admin/all/Pageable")
	public ResponseEntity<Page<Usuario>> findAllPageable(Pageable pageable){
		return ResponseEntity.ok(service.findAllPageable(pageable));
	}
	
	@GetMapping(value="/admin/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping(value = "/admin")
	public ResponseEntity<Usuario> insertUserAdmin(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody){
		return new ResponseEntity<Usuario>(service.insertUsuarioAdmin(usuarioPostRequestBody), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/user")
	public ResponseEntity<Usuario> insertUser(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody){
		return new ResponseEntity<Usuario>(service.insertUsuarioUser(usuarioPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody){
		service.update(usuarioPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
