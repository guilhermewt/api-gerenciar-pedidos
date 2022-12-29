package com.webserviceproject.util;

import com.webserviceproject.entities.OrderItems;

public class OrderItemsCreator {
	
	public static OrderItems createOrderItems() {
		OrderItems orderItems = new OrderItems(ProductCreator.createProduct(), OrderCreator.createOrder(), 1, 100.0);
		return orderItems;
	}
	
	public static OrderItems createOrderItemsToBeUpdate() {
		OrderItems orderItems = new OrderItems(ProductCreator.createProductToBeUpdate(), OrderCreator.createOrderToBeUpdate(), 2, 200.0);
		return orderItems;
	}
}
