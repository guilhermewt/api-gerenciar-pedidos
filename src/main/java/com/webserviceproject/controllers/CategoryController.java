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

import com.webserviceproject.mapper.CategoryMapper;
import com.webserviceproject.request.CategoryGetRequestBody;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;
import com.webserviceproject.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor

public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping(value = "/all/pageable")
	@Operation(summary = "find all categories paginated",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Page<CategoryGetRequestBody>> findAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity
				.ok(CategoryGetRequestBody.toCategoryGetRequestBody(categoryService.findAllPageable(pageable)));
	}

	@GetMapping(value = "/all")
	@Operation(summary = "find all categories non paginated",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<List<CategoryGetRequestBody>> findAllNonPageable() {
		return ResponseEntity
				.ok(CategoryMapper.INSTANCE.toCategoryGetRequestBody(categoryService.findAllNonPageable()));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "find category by id",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<CategoryGetRequestBody> findById(@PathVariable long id) {
		return ResponseEntity.ok(CategoryMapper.INSTANCE
				.toCategoryGetRequestBody(categoryService.findByIdOrElseThrowBadRequestException(id)));
	}

	@PostMapping(value = "/admin")
	@Operation(description = "the user has to be admin",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<CategoryGetRequestBody> insert(
			@RequestBody @Valid CategoryPostRequestBody categoryPostRequestBody) {

		return new ResponseEntity<CategoryGetRequestBody>(
				CategoryMapper.INSTANCE.toCategoryGetRequestBody(categoryService.insert(categoryPostRequestBody)),
				HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/admin/{id}")
	@Operation(description = "the user has to be admin",security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when category does not exist in the dataBase") })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/admin")
	@Operation(description = "the user has to be admin",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Void> update(@RequestBody CategoryPutRequestBody categoryPutRequestBody) {
		categoryService.updateCategory(categoryPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
