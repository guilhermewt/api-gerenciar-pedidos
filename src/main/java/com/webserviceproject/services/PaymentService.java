package com.webserviceproject.services;

import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Payment;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.mapper.PaymentMapper;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.repository.PaymentRepository;
import com.webserviceproject.request.PaymentPostRequestBody;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;

	public Payment findByOrderId(long id) {
		return paymentRepository.findByOrderId(id).orElseThrow(() -> new BadRequestException("payment not found"));
	}

	public Payment insert(PaymentPostRequestBody paymentPostRequestBody, Long idOrder) {
		Payment payment = PaymentMapper.INSTANCE.toPayment(paymentPostRequestBody);
		Order order = orderRepository.findById(idOrder).get();
		order.setOrderStatus(OrderStatus.PAID);
		payment.setOrder(order);

		return paymentRepository.save(payment);
	}

}
