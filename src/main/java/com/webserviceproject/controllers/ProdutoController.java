package com.webserviceproject.controllers;

import java.util.List;

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
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Produto>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> findById(@PathVariable long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping(value = "/admin/{categoriaId}")
	public ResponseEntity<Produto> save(@RequestBody ProdutoPostRequestBody produtoPostRequestBody,@PathVariable long categoriaId){
		return new ResponseEntity<Produto>(service.save(produtoPostRequestBody,categoriaId), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		service.deletarProduto(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/admin")
	public ResponseEntity<Void> update(@RequestBody ProdutoPutRequestBody produtoPutRequestBody){
		service.update(produtoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
