package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Payment;
import com.webserviceproject.request.PaymentGetRequestBody;
import com.webserviceproject.request.PaymentPostRequestBody;


@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
	
	public static PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
	
	public abstract Payment toPayment(PaymentPostRequestBody paymentPostRequestBody);
	
	public abstract PaymentGetRequestBody toPaymentGetRequestBody(Payment payment);

}
