package com.webserviceproject.request;

import java.time.Instant;
import java.util.function.Function;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Page;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PaymentGetRequestBody {

	@NotEmpty(message = "the id cannot be empty")
	private Long id;

	@NotEmpty(message = "the name cannot be empty")
	private Instant moment;
	
	private Order order;

	public PaymentGetRequestBody(Long id, Instant moment) {
		super();
		this.id = id;
		this.moment = moment;
	}

	public static Page<PaymentGetRequestBody> toPaymentGetRequestBody(Page<Payment> payment) {

		Page<PaymentGetRequestBody> dtoPage = payment.map(new Function<Payment, PaymentGetRequestBody>() {

			@Override
			public PaymentGetRequestBody apply(Payment payment) {
				PaymentGetRequestBody dto = new PaymentGetRequestBody();
				dto.setId(payment.getId());
				dto.setMoment(payment.getMoment());
				return dto;
			}

		});
		return dtoPage;
	}
	
	

}