package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
	private final ProdutoService service;
	
	@RequestMapping()
	public ResponseEntity<List<Produto>> findAll(){
		List<Produto> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> findById(@PathVariable long id){
		Produto produto = service.findById(id);
		return ResponseEntity.ok().body(produto);
	}
}
