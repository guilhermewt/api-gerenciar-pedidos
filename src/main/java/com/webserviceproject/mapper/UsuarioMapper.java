package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.request.UsuarioPostRequestBody;
import com.webserviceproject.request.UsuarioPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {
	
	public static UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	public abstract Usuario toUsuario(UsuarioPostRequestBody usuarioPostRequestBody); 
	
	public abstract Usuario AtualizarUsuario(UsuarioPutRequestBody usuarioPutRequestBody,
										  @MappingTarget Usuario usuario);	
}
