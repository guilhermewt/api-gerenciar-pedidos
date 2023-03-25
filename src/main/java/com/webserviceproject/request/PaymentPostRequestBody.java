package com.webserviceproject.request;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentPostRequestBody {
	
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;

	public PaymentPostRequestBody(Instant moment) {
		super();
		this.moment = moment;
	}
}
