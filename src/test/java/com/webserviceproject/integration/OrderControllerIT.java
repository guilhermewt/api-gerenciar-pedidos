package com.webserviceproject.integration;

import java.util.List;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.webserviceproject.data.JwtObject;
import com.webserviceproject.entities.Category;
import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Product;
import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.CategoryRepository;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.repository.ProductRepository;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.LoginGetRequestBody;
import com.webserviceproject.request.OrderGetRequestBody;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.ProductCreator;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;
import com.webserviceproject.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class OrderControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@LocalServerPort
	private int port;
	
	private static final UserDomain ADMIN = UserDomainCreator.createUserDomainAdmin();
	
	private static RoleModel ROLE_ADMIN = RoleModelCreator.createRoleModelADMIN();
	
	@TestConfiguration
	@Lazy
	static class config{
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}")int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	public HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtObject().getToken());
		return headers;
	}
	
	public JwtObject jwtObject() {
		LoginGetRequestBody login = new LoginGetRequestBody("username admin test","test");
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleAdmin.postForEntity("/login", login, JwtObject.class);
		return jwt.getBody();
	}
	
	@Test
	@DisplayName("findAll return list of Order whenSuccessful")
	void findAll_returnListOfOrder_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		this.orderRepository.save(OrderCreator.createOrder());
		
		List<OrderGetRequestBody> orderEntity = testRestTemplateRoleAdmin.exchange("/orders", HttpMethod.GET, new HttpEntity<>(headers()), new ParameterizedTypeReference<List<OrderGetRequestBody>>() {
		}).getBody();
		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(orderEntity.get(0)).isEqualTo(OrderCreator.createOrderGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findall return list of object page when successful")
	void findAll_returnObjectPage_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.orderRepository.save(OrderCreator.createOrder());
		
		PageableResponse<OrderGetRequestBody> orderEntity = testRestTemplateRoleAdmin.exchange("/orders/pageable", HttpMethod.GET,new HttpEntity<>(headers()),
										new ParameterizedTypeReference<PageableResponse<OrderGetRequestBody>>() {
										}).getBody();
		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.toList().get(0)).isNotNull();
		Assertions.assertThat(orderEntity.toList().get(0)).isEqualTo(OrderCreator.createOrderGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("findById Return order whenSuccessful")
	void findById_ReturnOrder_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Order order = this.orderRepository.save(OrderCreator.createOrder());
			
		OrderGetRequestBody orderEntity = testRestTemplateRoleAdmin.exchange("/orders/{id}", HttpMethod.GET,new HttpEntity<>(headers()),OrderGetRequestBody.class, order.getId()).getBody();
		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.getId()).isNotNull();
		Assertions.assertThat(orderEntity).isEqualTo(OrderCreator.createOrderGetRequestBodyCreator());
	}
	
	@Test
	@DisplayName("save return order whenSuccessful")
	void save_ReturnOrder_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Category category = this.categoryRepository.save(CategoryCreator.createCategory());
		Product product = ProductCreator.createProduct();
		product.getCategory().add(category);
		
		this.productRepository.save(product);
		
		OrderPostRequestBody orderPostRequestBody = OrderCreator.createOrderPostRequestBodyCreator();
		
		ResponseEntity<OrderGetRequestBody> orderEntity = testRestTemplateRoleAdmin.exchange("/orders/{productId}/{quantityOfProduct}", HttpMethod.POST,new HttpEntity<>(orderPostRequestBody,headers()), 
					OrderGetRequestBody.class,product.getId(),1);
		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.getBody()).isNotNull();
		Assertions.assertThat(orderEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(orderEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes order whenSuccessful")
	void delete_RemovesOrder_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.orderRepository.save(OrderCreator.createOrder());
	
		ResponseEntity<Void> orderEntity = testRestTemplateRoleAdmin.exchange("/orders/{orderId}", HttpMethod.DELETE, new HttpEntity<>(headers()), Void.class,
				OrderCreator.createOrder().getId());		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace order whenSuccessful")
	void update_ReplaceOrder_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.orderRepository.save(OrderCreator.createOrder());
		
		OrderPutRequestBody orderpost = OrderCreator.createOrderPutRequestBodyCreator();
			
		ResponseEntity<Void> orderEntity = testRestTemplateRoleAdmin.exchange("/orders", HttpMethod.PUT, 
				new HttpEntity<>(orderpost,headers()), Void.class);
		
		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	
}
