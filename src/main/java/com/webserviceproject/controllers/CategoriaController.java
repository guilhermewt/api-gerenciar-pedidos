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

	@GetMapping(value = "/all/pageable")
	public ResponseEntity<Page<Categoria>> findAllPageable(Pageable pageable) {
		return ResponseEntity.ok(service.findAllPageable(pageable));
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Categoria>> findAllNonPageable() {
		return ResponseEntity.ok(service.findAllNonPageable());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping(value = "/admin")
	public ResponseEntity<Categoria> insert(@RequestBody @Valid CategoriaPostRequestBody categoriaPostRequestBody){
		return new ResponseEntity<Categoria>(service.insert(categoriaPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.deletarUsuario(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/admin")
	public ResponseEntity<Void> update(@RequestBody CategoriaPutRequestBody categoriaPutRequestBody){
		service.atualizarCategoria(categoriaPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
