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

import com.webserviceproject.entities.Order;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;
import com.webserviceproject.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll(){
		return ResponseEntity.ok().body(orderService.findAllNonPageable());
	}
	
	@GetMapping(value = "/pageable")
	public ResponseEntity<Page<Order>> findAllPageable(Pageable pageable){
		return ResponseEntity.ok().body(orderService.findAllPageable(pageable));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Order> findById(@PathVariable long id){
		Order ped = orderService.findById(id);
		return ResponseEntity.ok().body(ped);
	}
	
	@PostMapping(value = "/{productId}/{quantityOfProduct}")
	public ResponseEntity<Order> save(@RequestBody @Valid OrderPostRequestBody orderPostRequestBody,
									   @PathVariable long productId,@PathVariable int quantityOfProduct){
		return new ResponseEntity<Order>(orderService.save(orderPostRequestBody, productId, quantityOfProduct), 
													  HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody OrderPutRequestBody orderPutRequestBody){
		orderService.update(orderPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{orderId}")
	public ResponseEntity<Void> delete(@PathVariable long orderId){
		orderService.delete(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
