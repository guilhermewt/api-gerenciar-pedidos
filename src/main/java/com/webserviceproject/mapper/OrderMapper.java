package com.webserviceproject.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Order;
import com.webserviceproject.request.OrderGetRequestBody;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
	
	public static OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	public abstract Order toOrder(OrderPostRequestBody orderPostRequestBody); 
	
	public abstract Order updateOrder(OrderPutRequestBody orderPutRequestBody,
										  @MappingTarget Order order);	
	
	public abstract List<OrderGetRequestBody> toOrderGetRequestBody(List<Order> order);
	
	public abstract OrderGetRequestBody toOrderGetRequestBody(Order order);
}
