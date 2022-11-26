package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.request.PedidoPostRequestBody;
import com.webserviceproject.request.PedidoPutRequestBody;
import com.webserviceproject.services.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "pedidos")
@RequiredArgsConstructor
public class PedidoController {
	
	private final PedidoService pedido;
	
	@RequestMapping
	public ResponseEntity<List<Pedido>> findAll(){
		List<Pedido> listPedido = pedido.findAll();
		return ResponseEntity.ok().body(listPedido);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> findById(@PathVariable long id){
		Pedido ped = pedido.findById(id);
		return ResponseEntity.ok().body(ped);
	}
	
	@PostMapping(value = "/{produtoId}/{quantidadeProduto}/{usuarioId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Pedido> save(@RequestBody PedidoPostRequestBody pedidoPostRequestBody,
									   @PathVariable long produtoId,@PathVariable int quantidadeProduto,
									   @PathVariable long usuarioId){
		return new ResponseEntity<Pedido>(pedido.save(pedidoPostRequestBody, produtoId, quantidadeProduto,usuarioId), 
													  HttpStatus.CREATED);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> update(@RequestBody PedidoPutRequestBody pedidoPutRequestBody){
		pedido.update(pedidoPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
