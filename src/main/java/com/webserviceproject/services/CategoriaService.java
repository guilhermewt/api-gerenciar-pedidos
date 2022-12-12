package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.mapper.CategoriaMapper;
import com.webserviceproject.repository.CategoriaRepositorio;
import com.webserviceproject.request.CategoriaPostRequestBody;
import com.webserviceproject.request.CategoriaPutRequestBody;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
	
	private final CategoriaRepositorio repositorio;
	
	public Page<Categoria> findAllPageable(Pageable pageable){
		return repositorio.findAll(pageable);
	}
	
	public List<Categoria> findAllNonPageable(){
		return repositorio.findAll();
	}
	
	public Categoria findById(long id) {
		return repositorio.findById(id).orElseThrow(() -> new BadRequestException("categoria not found"));
	}
	
	public Categoria insert(CategoriaPostRequestBody categoriaPostRequestBody) {
		return repositorio.save(CategoriaMapper.INSTANCE.toCategoria(categoriaPostRequestBody));
	}
	
	public void deletarUsuario(long idBook) {
		try {
			repositorio.delete(findById(idBook));
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void atualizarCategoria(CategoriaPutRequestBody categoriaPutRequestBody) {
		Categoria categoria = findById(categoriaPutRequestBody.getId());
				          
		CategoriaMapper.INSTANCE.atualizarCategoria(categoriaPutRequestBody,categoria);
		repositorio.save(categoria);
	}
}
