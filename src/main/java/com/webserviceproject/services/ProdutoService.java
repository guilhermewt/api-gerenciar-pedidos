package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.repository.ProdutoRepositorio;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepositorio repositorio;
	
	public List<Produto> findAll(){
		return repositorio.findAll();
	}
	
	public Produto findById(long id) {
		Optional<Produto> produto = repositorio.findById(id);
		return produto.get();
	}
}
