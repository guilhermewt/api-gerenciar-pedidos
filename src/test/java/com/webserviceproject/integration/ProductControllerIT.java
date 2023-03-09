package com.webserviceproject.integration;


import java.util.Arrays;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.webserviceproject.data.JwtObject;
import com.webserviceproject.entities.Product;
import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.CategoryRepository;
import com.webserviceproject.repository.ProductRepository;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.LoginGetRequestBody;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.ProductCreator;
import com.webserviceproject.util.ProductPostRequestBodyCreator;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;
import com.webserviceproject.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProductControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;

	@LocalServerPort
	private int port;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	private static final UserDomain ADMIN = UserDomainCreator.createUserDomainAdmin();
	private static final UserDomain USER = UserDomainCreator.createUserDomainRoleUser();
	
	private static RoleModel ROLE_ADMIN = RoleModelCreator.createRoleModelADMIN();
	private static RoleModel ROLE_USER = RoleModelCreator.createRoleModelUSER();
	
	@TestConfiguration
	@Lazy
	static class config{
		
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
		
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
		
	}
	
	public HttpHeaders headers() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwtObject().getToken());
		return httpHeaders;
	}
	
	public JwtObject jwtObject() {
		LoginGetRequestBody login = new LoginGetRequestBody("username admin test","test");
		ResponseEntity<JwtObject> jwt = testRestTemplateRoleAdmin.postForEntity("/login", login, JwtObject.class);
		return jwt.getBody();
	}
	
	@Test
	@DisplayName("findall return list of products whenSuccessful")
	void findAll_returnListOfProduct_WhenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Product product = this.productRepository.save(ProductCreator.createProduct());
		
		List<Product> productEntity = testRestTemplateRoleAdmin.exchange("/products/all", HttpMethod.GET, new HttpEntity<>(headers()), 
				new ParameterizedTypeReference<List<Product>>() {
		}).getBody();
		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(productEntity.get(0)).isEqualTo(product);
	}
	
	@Test
	@DisplayName("findall return list of object page when successful")
	void findAll_returnObjectPage_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Product product = this.productRepository.save(ProductCreator.createProduct());
		
		PageableResponse<Product> productEntity = testRestTemplateRoleAdmin.exchange("/products/all/pageable", HttpMethod.GET,new HttpEntity<>(headers()),
										new ParameterizedTypeReference<PageableResponse<Product>>() {
										}).getBody();
		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.toList().get(0)).isNotNull();
		Assertions.assertThat(productEntity.toList().get(0)).isEqualTo(product);
	}
	
	@Test
	@DisplayName("findById Return product whenSuccessful")
	void findById_ReturnProduct_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Product product = this.productRepository.save(ProductCreator.createProduct());
			
		Product productEntity = testRestTemplateRoleAdmin.exchange("/products/{id}", HttpMethod.GET,new HttpEntity<>(headers()),Product.class, product.getId()).getBody();
		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.getId()).isNotNull();
		Assertions.assertThat(productEntity).isEqualTo(product);
	}
	
	@Test
	@DisplayName("save Return Product whenSuccessful")
	void save_ReturnProduct_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.categoryRepository.save(CategoryCreator.createCategory());
		
		
		ProductPostRequestBody producPostRequestBody = ProductPostRequestBodyCreator.createProductPostRequestBodyCreator();
		
		ResponseEntity<Product> productEntity = testRestTemplateRoleAdmin.exchange("/products/admin/{categoryId}", HttpMethod.POST,new HttpEntity<>(producPostRequestBody,headers()), 
					Product.class,CategoryCreator.createCategory().getId());
		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.getBody()).isNotNull();
		Assertions.assertThat(productEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(productEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes product whenSuccessful")
	void delete_RemovesProduct_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.productRepository.save(ProductCreator.createProduct());
	
		ResponseEntity<Void> productEntity = testRestTemplateRoleAdmin.exchange("/products/admin/{id}", HttpMethod.DELETE, new HttpEntity<>(headers()), Void.class,
				ProductCreator.createProduct().getId());		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace product whenSuccessful")
	void update_ReplaceProduct_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Product product = productRepository.save(ProductCreator.createProduct());
		product.setName("name test 2");
			
		ResponseEntity<Void> productEntity = testRestTemplateRoleAdmin.exchange("/products/admin", HttpMethod.PUT, 
				new HttpEntity<>(product,headers()), Void.class);
		
		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("delete returns 403 when user is not admin") 
	void delete_Returns403_WhenUserIsNotAdmin() {
		this.roleModelRepository.saveAll(Arrays.asList(ROLE_ADMIN,ROLE_USER));
		this.userDomainRepository.save(USER);
		
		productRepository.save(ProductCreator.createProduct());
		
		ResponseEntity<Void> productEntity = testRestTemplateRoleUser.exchange("/products/admin/{id}", HttpMethod.DELETE, null, Void.class,
				ProductCreator.createProduct().getId());		

        Assertions.assertThat(productEntity).isNotNull();
		
		Assertions.assertThat(productEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.FORBIDDEN);	
	}
	
}	
