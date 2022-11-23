package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.mapper.CategoriaMapper;
import com.webserviceproject.repository.CategoriaRepositorio;
import com.webserviceproject.request.CategoriaPostRequestBody;
import com.webserviceproject.request.CategoriaPutRequestBody;
import com.webserviceproject.services.exceptions.DataBaseException;
import com.webserviceproject.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
	
	private final CategoriaRepositorio repositorio;
	
	public List<Categoria> findAll(){
		return repositorio.findAll();
	}
	
	public Categoria findById(long id) {
		return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Categoria insert(CategoriaPostRequestBody categoriaPostRequestBody) {
		return repositorio.save(CategoriaMapper.INSTANCE.toCategoria(categoriaPostRequestBody));
	}
	
	public void deletarUsuario(long idBook) {
		try {
			repositorio.delete(findById(idBook));
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	public void atualizarCategoria(CategoriaPutRequestBody categoriaPutRequestBody) {
		Categoria categoria = repositorio.findById(categoriaPutRequestBody.getId())
				          .orElseThrow(() -> new ResourceNotFoundException(categoriaPutRequestBody.getId()));
		
		CategoriaMapper.INSTANCE.atualizarCategoria(categoriaPutRequestBody,categoria);
		repositorio.save(categoria);
	}
}
