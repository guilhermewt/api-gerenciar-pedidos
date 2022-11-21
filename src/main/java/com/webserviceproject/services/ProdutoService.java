package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.repository.ProdutoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
	
	private final ProdutoRepositorio repositorio;
	
	public List<Produto> findAll(){
		return repositorio.findAll();
	}
	
	public Produto findById(long id) {
		Optional<Produto> produto = repositorio.findById(id);
		return produto.get();
	}
}
