package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.enums.OrderStatus;

public class OrderCreator {
	
	public static Order createOrder() {
		Order order = new Order(1l, Instant.now(), OrderStatus.WAITING_PAYMENT);
		return order;
	}
	
	public static Order createOrderToBeUpdate() {
		Order order = new Order(1l, Instant.now(), OrderStatus.PAID);
		return order;
	}
}
