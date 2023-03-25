package com.webserviceproject.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.webserviceproject.entities.Payment;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.repository.PaymentRepository;
import com.webserviceproject.services.PaymentService;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.PaymentCreator;


@ExtendWith(SpringExtension.class)
public class PaymentServiceTest {
	//fazer test controler e integration metodo get no order.payment e comparar
	
	@InjectMocks
	private PaymentService paymentService;
	
	@Mock
	private PaymentRepository paymentRepositoryMock;
	
	@Mock
	private OrderRepository orderRepositoryMock;


	@BeforeEach
	void setUp() {

		BDDMockito.when(paymentRepositoryMock.save(ArgumentMatchers.any(Payment.class))).thenReturn(PaymentCreator.createPayment());

		BDDMockito.when(orderRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(OrderCreator.createOrder()));
		
		BDDMockito.when(paymentRepositoryMock.findByOrderId(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(PaymentCreator.createPayment()));
	}
	
	
	@Test
	@DisplayName("findByOrderId return payment whenSuccessful")
	void findByOrderId_ReturnPayment_whenSuccessful() {
		Payment paymentSaved = PaymentCreator.createPayment();

		Payment payment = this.paymentService.findByOrderId(1l);

		Assertions.assertThat(payment).isNotNull();
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment).isEqualTo(paymentSaved);
	}
	
	@Test
	@DisplayName("save  return payment whenSuccessful")
	void save_ReturnPayment_whenSuccessful() {
		Payment paymentSaved = PaymentCreator.createPayment();

		Payment payment = this.paymentService
				.insert(PaymentCreator.createPaymentPostRequestBodyCreator(),1l);
		
		Assertions.assertThat(payment).isNotNull();
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment).isEqualTo(paymentSaved);
	}
	
	
}
