package com.webserviceproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	/*no downgrade as exception estao funcionando mas vale resaltar que a url estava errada*/
	
	@GetMapping(value = "/all/pageable")
	@Operation(summary = "find all categories paginated")
	public ResponseEntity<Page<Category>> findAllPageable(@ParameterObject Pageable pageable){
		return ResponseEntity.ok(categoryService.findAllPageable(pageable));
	}
	
	@GetMapping(value = "/all")
	@Operation(summary = "find all categories non paginated")
	public ResponseEntity<List<Category>> findAllNonPageable() {
		return ResponseEntity.ok(categoryService.findAllNonPageable());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "find category by id")
	public ResponseEntity<Category> findById(@PathVariable long id) {
		return ResponseEntity.ok(categoryService.findByIdOrElseThrowBadRequestException(id));
	}
	
	@PostMapping(value = "/admin")
	@Operation(description="the user has to be admin")
	public ResponseEntity<Category> insert(@RequestBody @Valid CategoryPostRequestBody categoryPostRequestBody){
		return new ResponseEntity<Category>(categoryService.insert(categoryPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	@Operation(description="the user has to be admin")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when category does not exist in the dataBase")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/admin")
	@Operation(description="the user has to be admin")
	public ResponseEntity<Void> update(@RequestBody CategoryPutRequestBody categoryPutRequestBody){
		categoryService.updateCategory(categoryPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
