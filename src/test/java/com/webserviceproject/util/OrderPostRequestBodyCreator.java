package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.request.OrderPostRequestBody;

public class OrderPostRequestBodyCreator {
	
	public static OrderPostRequestBody createOrderPostRequestBodyCreator() {
		return new OrderPostRequestBody(Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
	}
}
