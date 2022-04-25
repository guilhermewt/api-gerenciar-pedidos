package com.webserviceproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.services.PedidoService;

@RestController
@RequestMapping(value = "pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedido;
	
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
}
