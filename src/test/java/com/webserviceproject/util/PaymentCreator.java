package com.webserviceproject.util;

import java.time.Instant;

import com.webserviceproject.entities.Payment;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.request.PaymentGetRequestBody;
import com.webserviceproject.request.PaymentPostRequestBody;

public class PaymentCreator {
	
	public static Payment createPayment() {
		Payment payment = new Payment(1l, Instant.parse("2023-03-18T00:00:00Z"));
		payment.setOrder(OrderCreator.createOrder());
		payment.getOrder().setOrderStatus(OrderStatus.PAID);
		
		return payment;
	}
	
	public static PaymentPostRequestBody createPaymentPostRequestBodyCreator() {
		return new PaymentPostRequestBody(Instant.parse("2023-03-18T00:00:00Z"));
	}

	public static PaymentGetRequestBody createPaymentGetRequestBodyCreator() {
		PaymentGetRequestBody payment = new PaymentGetRequestBody(1l, Instant.parse("2023-03-18T00:00:00Z"));
		payment.setOrder(OrderCreator.createOrder());
		payment.getOrder().setOrderStatus(OrderStatus.PAID);
		return payment;
	}
	
	public static PaymentGetRequestBody createPaymentGetRequestToBeUpdateCreator() {
		PaymentGetRequestBody payment = new PaymentGetRequestBody(1l, Instant.parse("2023-03-18T00:00:00Z"));
		return payment;
	}
}
