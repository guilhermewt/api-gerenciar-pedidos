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

import com.webserviceproject.entities.Product;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.request.ProductPutRequestBody;
import com.webserviceproject.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {
    
	private final ProductService productService;
	
	@GetMapping(value = "/all/pageable")
	public ResponseEntity<Page<Product>> findAllPageable(Pageable pageable){
		return ResponseEntity.ok(productService.findAllPageable(pageable));
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Product>> findAllNonPageable(){
		return ResponseEntity.ok(productService.findAllNonPageable());
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> findById(@PathVariable long id){
		return ResponseEntity.ok(productService.findById(id));
	}
	
	@PostMapping(value = "/admin/{categoryId}")
	public ResponseEntity<Product> save(@RequestBody @Valid ProductPostRequestBody productPostRequestBody,
										@PathVariable long categoryId){
		return new ResponseEntity<Product>(productService.save(productPostRequestBody,categoryId), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/admin")
	public ResponseEntity<Void> update(@RequestBody ProductPutRequestBody productPutRequestBody){
		productService.update(productPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
