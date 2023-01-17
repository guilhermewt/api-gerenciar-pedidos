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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.webserviceproject.entities.Category;
import com.webserviceproject.entities.Product;
import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.CategoryRepository;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.CategoryPostRequestBodyCreator;
import com.webserviceproject.util.ProductCreator;
import com.webserviceproject.util.ProductPostRequestBodyCreator;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;
import com.webserviceproject.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	
	@LocalServerPort
	private int port;
	
	private static RoleModel ROLE_ADMIN = RoleModelCreator.createRoleModelADMIN();
	private static RoleModel ROLE_USER = RoleModelCreator.createRoleModelUSER();
	
	private static final UserDomain ADMIN = UserDomainCreator.createUserDomainAdmin();
	private static final UserDomain USER = UserDomainCreator.createUserDomainRoleUser();
	
	@TestConfiguration
	@Lazy
	static class config{	
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("username admin test", "test");
			return new TestRestTemplate(restTemplateBuilder);
		}
		
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("username test","test");
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("findall return list of category whenSuccessful")
	void findAll_returnListOfCategory_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Category category = this.categoryRepository.save(CategoryCreator.createCategory());
		
		List<Category> entityCategory = testRestTemplateRoleAdmin.exchange("/categories/all", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Category>>() {
		}).getBody();
		
		
		Assertions.assertThat(entityCategory).isNotNull();
		Assertions.assertThat(entityCategory.get(0)).isNotNull();
		Assertions.assertThat(entityCategory.get(0)).isEqualTo(category);
	}
	
	@Test
	@DisplayName("findall return list of object page when successful")
	void findAll_returnObjectPage_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Category category = this.categoryRepository.save(CategoryCreator.createCategory());
		
		PageableResponse<Category> categoryEntity = testRestTemplateRoleAdmin.exchange("/categories/all/pageable", HttpMethod.GET,null,
										new ParameterizedTypeReference<PageableResponse<Category>>() {
										}).getBody();
		
		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.toList().get(0)).isNotNull();
		Assertions.assertThat(categoryEntity.toList().get(0)).isEqualTo(category);
	}
	
	@Test
	@DisplayName("findById Return category whenSuccessful")
	void findById_ReturnCategory_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Category category = this.categoryRepository.save(CategoryCreator.createCategory());
			
		Category categoryEntity = testRestTemplateRoleAdmin.getForObject("/categories/{id}", Category.class, category.getId());
		
		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.getId()).isNotNull();
		Assertions.assertThat(categoryEntity).isEqualTo(category);
	}
	
	@Test
	@DisplayName("save Return Category whenSuccessful")
	void save_ReturnCategory_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		CategoryPostRequestBody categoryPostRequestBody = CategoryPostRequestBodyCreator.createCategoryPostRequestBodyCreator();
		
		ResponseEntity<Category> categoryEntity = testRestTemplateRoleAdmin.postForEntity("/categories/admin", categoryPostRequestBody, 
					Category.class);
		
		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.getBody()).isNotNull();
		Assertions.assertThat(categoryEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(categoryEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes category whenSuccessful")
	void delete_RemovesCategory_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		this.categoryRepository.save(CategoryCreator.createCategory());
	
		ResponseEntity<Void> categoryEntity = testRestTemplateRoleAdmin.exchange("/categories/admin/{id}", HttpMethod.DELETE, null, Void.class,
				CategoryCreator.createCategory().getId());		
		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace category whenSuccessful")
	void update_ReplaceCategory_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		Category category = categoryRepository.save(CategoryCreator.createCategory());
		category.setName("name test 2");
			
		ResponseEntity<Void> categoryEntity = testRestTemplateRoleAdmin.exchange("/categories/admin", HttpMethod.PUT, 
				new HttpEntity<>(category), Void.class);
		
		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("delete returns 403 when user is not admin") 
	void delete_Returns403_WhenUserIsNotAdmin() {
		this.roleModelRepository.saveAll(Arrays.asList(ROLE_ADMIN,ROLE_USER));
		this.userDomainRepository.save(USER);
		
		categoryRepository.save(CategoryCreator.createCategory());
		
		ResponseEntity<Void> categoryEntity = testRestTemplateRoleUser.exchange("/categories/admin/{id}", HttpMethod.DELETE, null, Void.class,
				CategoryCreator.createCategory().getId());		

        Assertions.assertThat(categoryEntity).isNotNull();
		
		Assertions.assertThat(categoryEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.FORBIDDEN);	
	}
	
}
