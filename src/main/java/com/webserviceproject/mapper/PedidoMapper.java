package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.request.PedidoPostRequestBody;
import com.webserviceproject.request.PedidoPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class PedidoMapper {
	
	public static PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);
	
	public abstract Pedido toPedido(PedidoPostRequestBody pedidoPostRequestBody); 
	
	public abstract Pedido updatePedido(PedidoPutRequestBody pedidoPutRequestBody,
										  @MappingTarget Pedido pedido);	
}
