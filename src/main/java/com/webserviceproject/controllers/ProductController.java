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

import com.webserviceproject.mapper.ProductMapper;
import com.webserviceproject.request.ProductGetRequestBody;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.request.ProductPutRequestBody;
import com.webserviceproject.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping(value = "/all/pageable")
	@Operation(summary = "find all products paginated", description = "the default size is 20, use the parameter to change the default value", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Page<ProductGetRequestBody>> findAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity
				.ok(ProductGetRequestBody.toProductGetRequestBodyPage(productService.findAllPageable(pageable)));
	}

	@GetMapping(value = "/all")
	@Operation(summary = "find all products non paginated", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<List<ProductGetRequestBody>> findAllNonPageable() {
		return ResponseEntity.ok(ProductMapper.INSTANCE.toProductGetRequestBody(productService.findAllNonPageable()));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "find product by id", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<ProductGetRequestBody> findById(@PathVariable long id) {
		return ResponseEntity.ok(ProductMapper.INSTANCE
				.toProductGetRequestBody(productService.findByIdOrElseThrowBadRequestException(id)));
	}

	@PostMapping(value = "/admin/{categoryId}")
	@Operation(description = "for the product to be made,the category Id are required and the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<ProductGetRequestBody> save(@RequestBody @Valid ProductPostRequestBody productPostRequestBody,
			@PathVariable long categoryId) {
		return new ResponseEntity<ProductGetRequestBody>(
				ProductMapper.INSTANCE.toProductGetRequestBody(productService.save(productPostRequestBody, categoryId)),
				HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/admin/{id}")
	@Operation(description = "the user has to be admin", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when product does not exist in the dataBase") })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/admin")
	@Operation(description = "the user has to be admin", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Void> update(@RequestBody ProductPutRequestBody productPutRequestBody) {
		productService.update(productPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
