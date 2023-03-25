package com.webserviceproject.controller;

import static org.mockito.ArgumentMatchers.anyLong;

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

import com.webserviceproject.controllers.PaymentController;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.request.PaymentGetRequestBody;
import com.webserviceproject.request.PaymentPostRequestBody;
import com.webserviceproject.services.PaymentService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.PaymentCreator;

@ExtendWith(SpringExtension.class)
public class PaymentControllerTest {
/*A solucao sera a seguinte o order vai ver apenas o status de pagamento PAID, 
 * o payment mostrar o order */
	@InjectMocks
	private PaymentController paymentController;

	@Mock
	private PaymentService paymentServiceMock;
	
	@Mock
	private OrderRepository OrderRepositoryMock;

	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;

	@BeforeEach
	void setUp() {

		BDDMockito.when(OrderRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(OrderCreator.createOrder()));
		
		BDDMockito.when(paymentServiceMock.findByOrderId(ArgumentMatchers.anyLong()))
		.thenReturn(PaymentCreator.createPayment());

		BDDMockito
				.when(paymentServiceMock
						.insert(ArgumentMatchers.any(PaymentPostRequestBody.class),anyLong()))
				.thenReturn(PaymentCreator.createPayment());
	}


	@Test
	@DisplayName("findByOrderId return payment whenSuccessful")
	void findByOrderId_ReturnPayment_whenSuccessful() {
		PaymentGetRequestBody paymentSaved = PaymentCreator.createPaymentGetRequestBodyCreator();

		PaymentGetRequestBody payment = this.paymentController.findPaymentByOrderId(1l).getBody();

		Assertions.assertThat(payment).isNotNull();
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment).isEqualTo(paymentSaved);
	}

	@Test
	@DisplayName("save return payment whenSuccessful")
	void save_ReturnPayment_whenSuccessful() {
		PaymentGetRequestBody paymentSaved = PaymentCreator.createPaymentGetRequestBodyCreator();

		PaymentGetRequestBody payment = this.paymentController
				.insert(PaymentCreator.createPaymentPostRequestBodyCreator(),1l).getBody();
	
		Assertions.assertThat(payment).isNotNull();
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment).isEqualTo(paymentSaved);
	}

	
}
