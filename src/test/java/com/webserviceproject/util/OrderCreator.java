package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.enums.OrderStatus;

public class OrderCreator {
	
	public static Order createOrder() {
		Order order = new Order(1l, Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
		order.setUserDomain(UserDomainCreator.createUserDomain());
		return order;
	}
	
	public static Order createOrderToBeUpdate() {
		Order order = new Order(1l, Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.PAID);
		return order;
	}
}
