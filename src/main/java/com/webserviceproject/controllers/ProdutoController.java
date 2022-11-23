package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.request.ProdutoPostRequestBody;
import com.webserviceproject.request.ProdutoPutRequestBody;
import com.webserviceproject.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
	private final ProdutoService service;
	
	@RequestMapping(value = "/all")
	public ResponseEntity<List<Produto>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> findById(@PathVariable long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Produto> save(@RequestBody ProdutoPostRequestBody produtoPostRequestBody){
		return new ResponseEntity<Produto>(service.save(produtoPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.deletarProduto(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> update(@RequestBody ProdutoPutRequestBody produtoPutRequestBody){
		service.update(produtoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
