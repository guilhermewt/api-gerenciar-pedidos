package com.webserviceproject.integration;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.webserviceproject.data.JwtObject;
import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Payment;
import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.repository.PaymentRepository;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.LoginGetRequestBody;
import com.webserviceproject.request.PaymentGetRequestBody;
import com.webserviceproject.request.PaymentPostRequestBody;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.PaymentCreator;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;

import lombok.extern.log4j.Log4j2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Log4j2
public class PaymentControllerIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	

	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserDomainRepository userDomainRepository;

	@Autowired
	private RoleModelRepository roleModelRepository;

	@Autowired
	private OrderRepository orderRepository;

	@LocalServerPort
	private int port;

	private static RoleModel ROLE_ADMIN = RoleModelCreator.createRoleModelADMIN();
	private static RoleModel ROLE_USER = RoleModelCreator.createRoleModelUSER();

	private static final UserDomain ADMIN = UserDomainCreator.createUserDomainAdmin();
	private static final UserDomain USER = UserDomainCreator.createUserDomainRoleUser();

	@TestConfiguration
	@Lazy
	static class config {
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}

		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}

	public HttpHeaders headers() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwtObject().getToken());
		return httpHeaders;
	}

	public JwtObject jwtObject() {
		LoginGetRequestBody login = new LoginGetRequestBody("username admin test", "test");
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleAdmin.postForEntity("/login", login, JwtObject.class);
		return jwt.getBody();
	}

	@Test
	@DisplayName("findById Return payment whenSuccessful")
	void findById_ReturnPayment_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.orderRepository.save(OrderCreator.createOrder());
		
		Order order = orderRepository.findById(1l).get();
		order.setOrderStatus(OrderStatus.PAID);
		this.orderRepository.save(order);
	
		Payment payment = new Payment(1l, Instant.parse("2023-03-18T00:00:00Z"));
		payment.setOrder(order);
	    this.paymentRepository.save(payment);


		PaymentGetRequestBody paymentEntity = testRestTemplateRoleAdmin.exchange("/payment/findOrderId/{idOrder}",
				HttpMethod.GET, new HttpEntity<>(headers()), PaymentGetRequestBody.class, 1l).getBody();

		Assertions.assertThat(paymentEntity).isNotNull();
		Assertions.assertThat(paymentEntity.getId()).isNotNull();
		Assertions.assertThat(paymentEntity).isEqualTo(PaymentCreator.createPaymentGetRequestBodyCreator());
	}

	@Test
	@DisplayName("save Return Payment whenSuccessful")
	void save_ReturnPayment_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.orderRepository.save(OrderCreator.createOrder());

		PaymentPostRequestBody paymentPostRequestBody = PaymentCreator.createPaymentPostRequestBodyCreator();

		ResponseEntity<PaymentGetRequestBody> paymentEntity = testRestTemplateRoleAdmin.exchange("/payment/{idOrder}",
				HttpMethod.POST, new HttpEntity<>(paymentPostRequestBody, headers()), PaymentGetRequestBody.class, 1l);

		Assertions.assertThat(paymentEntity).isNotNull();
		Assertions.assertThat(paymentEntity.getBody()).isNotNull();
		Assertions.assertThat(paymentEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(paymentEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

}
