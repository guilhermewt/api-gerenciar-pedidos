package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.repository.PedidoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
	
	private final PedidoRepositorio repositorio;
	
	public List<Pedido> findAll(){
		return repositorio.findAll();
	}
	
	public Pedido findById(long id) {
		Optional<Pedido> usuario = repositorio.findById(id);
		return usuario.get();
	}
}
