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

import com.webserviceproject.entities.Category;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;
import com.webserviceproject.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping(value = "/all/pageable")
	public ResponseEntity<Page<Category>> findAllPageable(Pageable pageable) {
		return ResponseEntity.ok(categoryService.findAllPageable(pageable));
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Category>> findAllNonPageable() {
		return ResponseEntity.ok(categoryService.findAllNonPageable());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}
	
	@PostMapping(value = "/admin")
	public ResponseEntity<Category> insert(@RequestBody @Valid CategoryPostRequestBody categoryPostRequestBody){
		return new ResponseEntity<Category>(categoryService.insert(categoryPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		categoryService.deletarUsuario(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/admin")
	public ResponseEntity<Void> update(@RequestBody CategoryPutRequestBody categoryPutRequestBody){
		categoryService.atualizarCategoria(categoryPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
