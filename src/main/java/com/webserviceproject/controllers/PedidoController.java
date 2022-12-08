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

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.request.PedidoPostRequestBody;
import com.webserviceproject.request.PedidoPutRequestBody;
import com.webserviceproject.services.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/pedidos")
@RequiredArgsConstructor
public class PedidoController {
	
	private final PedidoService pedidoService;
	
	@GetMapping
	public ResponseEntity<List<Pedido>> findAll(){
		List<Pedido> listPedido = pedidoService.findAllNonPageable();
		return ResponseEntity.ok().body(listPedido);
	}
	
	@GetMapping(value = "/pageable")
	public ResponseEntity<Page<Pedido>> findAllPageable(Pageable pageable){
		return ResponseEntity.ok().body(pedidoService.findAllPageable(pageable));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable long id){
		Pedido ped = pedidoService.findById(id);
		return ResponseEntity.ok().body(ped);
	}
	
	@PostMapping(value = "/{produtoId}/{quantidadeProduto}")
	public ResponseEntity<Pedido> save(@RequestBody @Valid PedidoPostRequestBody pedidoServicePostRequestBody,
									   @PathVariable long produtoId,@PathVariable int quantidadeProduto){
		return new ResponseEntity<Pedido>(pedidoService.save(pedidoServicePostRequestBody, produtoId, quantidadeProduto), 
													  HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody PedidoPutRequestBody pedidoServicePutRequestBody){
		pedidoService.update(pedidoServicePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{idPedido}")
	public ResponseEntity<Void> delete(@PathVariable long idPedido){
		pedidoService.delete(idPedido);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
