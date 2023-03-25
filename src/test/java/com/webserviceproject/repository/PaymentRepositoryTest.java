package com.webserviceproject.repository;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Payment;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.util.PaymentCreator;

@DataJpaTest
@DisplayName("test for payment repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PaymentRepositoryTest {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
		
	@Test
	@DisplayName("findByOrderId return payment whenSuccessful")
	void findByOrderId_ReturnPayment_whenSuccessful() {
		this.orderRepository.save(new Order(null, Instant.parse("2023-03-18T00:00:00Z"), OrderStatus.WAITING_PAYMENT));

		Payment paymentSaved = this.paymentRepository.save(PaymentCreator.createPayment());
		
		Payment payment = this.paymentRepository.findByOrderId(1l).get();

		Assertions.assertThat(payment).isNotNull();
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment).isEqualTo(paymentSaved);
	}
	
	@Test
	@DisplayName("save return payment whenSuccessful")
	void save_returnpayment_whenSuccessful() {	
		this.orderRepository.save(new Order(null, Instant.parse("2023-03-18T00:00:00Z"), OrderStatus.WAITING_PAYMENT));
		
		Payment paymentSaved = this.paymentRepository.save(PaymentCreator.createPayment());
		
		Assertions.assertThat(paymentSaved).isNotNull();
		Assertions.assertThat(paymentSaved.getId()).isNotNull();
		Assertions.assertThat(paymentSaved).isEqualTo(PaymentCreator.createPayment());
	}
	
	
}
