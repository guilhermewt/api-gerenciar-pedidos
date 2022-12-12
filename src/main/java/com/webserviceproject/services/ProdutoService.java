package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.mapper.ProdutoMapper;
import com.webserviceproject.repository.ProdutoRepositorio;
import com.webserviceproject.request.ProdutoPostRequestBody;
import com.webserviceproject.request.ProdutoPutRequestBody;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
	
	private final ProdutoRepositorio repositorio;
	private final CategoriaService categoriaService;
	
	public Page<Produto> findAllPageable(Pageable pageable){
		return repositorio.findAll(pageable);
	}
	
	public List<Produto> findAllNonPageable(){
		return repositorio.findAll();
	}
	
	public Produto findById(long id) {
		return repositorio.findById(id).orElseThrow(() -> new BadRequestException("produto not found"));
	}
	
	public Produto save(ProdutoPostRequestBody produtoPostRequestBody,long categoriaId) {
		Produto produto = ProdutoMapper.INSTANCE.toProduto(produtoPostRequestBody);
		produto.getCategoria().add(categoriaService.findById(categoriaId));
		return repositorio.save(produto);
	}
	
	public void deletarProduto(long id) {
		try {
			repositorio.delete(findById(id));
		}
		catch(DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void update (ProdutoPutRequestBody produtoPutRequestBody) {
		Produto produtoSalvo = findById(produtoPutRequestBody.getId());
		
		ProdutoMapper.INSTANCE.updateProduto(produtoPutRequestBody, produtoSalvo);
		
		repositorio.save(produtoSalvo);
	}
}
