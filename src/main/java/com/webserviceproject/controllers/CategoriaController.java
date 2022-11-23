package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.request.CategoriaPostRequestBody;
import com.webserviceproject.request.CategoriaPutRequestBody;
import com.webserviceproject.services.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/categorias")
@RequiredArgsConstructor
public class CategoriaController {

	private final CategoriaService service;

	@RequestMapping(value = "/all")
	public ResponseEntity<List<Categoria>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> findById(@PathVariable long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Categoria> insert(@RequestBody CategoriaPostRequestBody categoriaPostRequestBody){
		return new ResponseEntity<Categoria>(service.insert(categoriaPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.deletarUsuario(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> update(@RequestBody CategoriaPutRequestBody categoriaPutRequestBody){
		service.atualizarCategoria(categoriaPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
