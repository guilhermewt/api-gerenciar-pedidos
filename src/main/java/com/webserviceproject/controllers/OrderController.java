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

import com.webserviceproject.mapper.OrderMapper;
import com.webserviceproject.request.OrderGetRequestBody;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;
import com.webserviceproject.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	@Operation(summary = "find all books non paginated", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<List<OrderGetRequestBody>> findAll() {
		return ResponseEntity.ok().body(OrderMapper.INSTANCE.toOrderGetRequestBody(orderService.findAllNonPageable()));
	}

	@GetMapping(value = "/pageable")
	@Operation(summary = "find all books paginated", description = "the default size is 20, use the parameter to change the default value", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Page<OrderGetRequestBody>> findAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok()
				.body(OrderGetRequestBody.toOrderGetRequestBodyPage(orderService.findAllPageable(pageable)));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "find category by id", security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<OrderGetRequestBody> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(
				OrderMapper.INSTANCE.toOrderGetRequestBody(orderService.findByIdOrElseThrowNewBadRequestException(id)));
	}

	@PostMapping(value = "/{productId}/{quantityOfProduct}")
	@Operation(description = "for the order to be made,the product Id and the quantity of product are required", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<OrderGetRequestBody> save(@RequestBody @Valid OrderPostRequestBody orderPostRequestBody,
			@PathVariable long productId, @PathVariable int quantityOfProduct) {

		return new ResponseEntity<OrderGetRequestBody>(OrderMapper.INSTANCE.toOrderGetRequestBody(
				orderService.save(orderPostRequestBody, productId, quantityOfProduct)), HttpStatus.CREATED);
	}

	@PutMapping
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })

	public ResponseEntity<Void> update(@RequestBody OrderPutRequestBody orderPutRequestBody) {
		orderService.update(orderPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{orderId}")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when order does not exist in the dataBase") })
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })

	public ResponseEntity<Void> delete(@PathVariable long orderId) {
		orderService.delete(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
