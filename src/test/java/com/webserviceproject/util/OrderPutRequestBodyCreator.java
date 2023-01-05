package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.request.OrderPutRequestBody;

public class OrderPutRequestBodyCreator {
	
	public static OrderPutRequestBody createOrderPutRequestBodyCreator() {
		return new OrderPutRequestBody(1l, Instant.parse("2023-01-01T23:59:59Z"), OrderStatus.PAID);
	}
}
