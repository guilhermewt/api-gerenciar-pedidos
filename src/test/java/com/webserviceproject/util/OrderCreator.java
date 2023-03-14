package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.request.OrderGetRequestBody;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;

public class OrderCreator {
	
	public static Order createOrder() {
		Order order = new Order(1l, Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
		order.setUserDomain(UserDomainCreator.createUserDomainAdmin());
		return order;
	}
	
	public static Order createOrderToBeUpdate() {
		Order order = new Order(1l, Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.PAID);
		return order;
	}
	
	public static OrderPostRequestBody createOrderPostRequestBodyCreator() {
		return new OrderPostRequestBody(Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
	}
	
	public static OrderPutRequestBody createOrderPutRequestBodyCreator() {
		return new OrderPutRequestBody(1l, Instant.parse("2023-01-01T23:59:59Z"), OrderStatus.PAID);
	}
	
	public static OrderGetRequestBody createOrderGetRequestBodyCreator() {
		return new OrderGetRequestBody(1l,Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
	}
}
