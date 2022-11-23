package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Produto;
import com.webserviceproject.request.ProdutoPostRequestBody;
import com.webserviceproject.request.ProdutoPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class ProdutoMapper {
	
	public static ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
	
	public abstract Produto toProduto(ProdutoPostRequestBody produtoPostRequestBody);
	
	public abstract Produto updateProduto(ProdutoPutRequestBody produtoPutRequestBody, @MappingTarget Produto produto);
}
