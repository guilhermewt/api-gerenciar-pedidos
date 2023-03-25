package com.webserviceproject.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.mapper.PaymentMapper;
import com.webserviceproject.request.PaymentGetRequestBody;
import com.webserviceproject.request.PaymentPostRequestBody;
import com.webserviceproject.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping(value = "/findOrderId/{idOrder}")
	@Operation(description = "find payment by order id",security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<PaymentGetRequestBody> findPaymentByOrderId(@PathVariable Long idOrder) {

		return new ResponseEntity<PaymentGetRequestBody>(
				PaymentMapper.INSTANCE.toPaymentGetRequestBody(paymentService.findByOrderId(idOrder)),
				HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/{idOrder}")
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<PaymentGetRequestBody> insert(
			@RequestBody @Valid PaymentPostRequestBody paymentPostRequestBody,@PathVariable long idOrder) {

		return new ResponseEntity<PaymentGetRequestBody>(
				PaymentMapper.INSTANCE.toPaymentGetRequestBody(paymentService.insert(paymentPostRequestBody,idOrder)),
				HttpStatus.CREATED);
	}

}
