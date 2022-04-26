package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.repository.CategoriaRepositorio;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepositorio repositorio;
	
	public List<Categoria> findAll(){
		return repositorio.findAll();
	}
	
	public Categoria findById(long id) {
		Optional<Categoria> usuario = repositorio.findById(id);
		return usuario.get();
	}
}
