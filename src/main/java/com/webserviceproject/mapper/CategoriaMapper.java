package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Categoria;
import com.webserviceproject.request.CategoriaPostRequestBody;
import com.webserviceproject.request.CategoriaPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class CategoriaMapper {
	
	public static CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
	
	public abstract Categoria toCategoria(CategoriaPostRequestBody categoriaPostRequestBody);
	
	public abstract Categoria atualizarCategoria(CategoriaPutRequestBody categoriaPutRequestBody, 
											  @MappingTarget Categoria categoria);
}
