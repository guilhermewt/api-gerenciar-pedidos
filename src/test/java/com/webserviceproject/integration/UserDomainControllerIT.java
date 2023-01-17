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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;
import com.webserviceproject.util.UserDomainPutRequestBodyCreator;
import com.webserviceproject.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDomainControllerIT {
	//metodo 403,usercomum e admin,admin update qualquer um,update nao funcionou
	//so falta o metodo updateFUll
	//todos metodos funcionando,falta ver as excessoes,e testa os testes como mvn e fazer as configuracoes no pom,fazer commit
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	@Qualifier(value = "testRestTemplateWithNonRoles")
	private TestRestTemplate testRestTemplateWithNonRoles;
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Autowired
	private RoleModelRepository roleModelRepository;
	
	private static UserDomain ADMIN = UserDomainCreator.createUserDomainAdmin();
	private static final UserDomain USER = UserDomainCreator.createUserDomainRoleUser();
	
	private static RoleModel ROLE_ADMIN = RoleModelCreator.createRoleModelADMIN();
	private static RoleModel ROLE_USER = RoleModelCreator.createRoleModelUSER();
	
	@TestConfiguration
	@Lazy
	static class config{
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdmin(@Value("${local.server.port}")int port) {
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
		
		@Bean(name = "testRestTemplateWithNonRoles")
		public TestRestTemplate testRestTemplateWithNonRoles(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("findall return list of userDomain whenSuccessful")
	void findAll_returnListOfUserDomain_whenSuccessful() {
		this.roleModelRepository.save(roleModelRepository.save(ROLE_ADMIN));
		UserDomain userDomainToBeSaved = this.userDomainRepository.save(ADMIN);
		
		List<UserDomain> userDomainEntity = testRestTemplateRoleAdmin.exchange("/userdomains/admin/all/", HttpMethod.GET,null, 
				new ParameterizedTypeReference<List<UserDomain>>() {
				}
		).getBody();
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(userDomainEntity.get(0)).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("findall return list of object page when successful")
	void findAll_returnObjectPage_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		UserDomain userDomain =this.userDomainRepository.save(ADMIN);
		
		PageableResponse<UserDomain> userDomainEntity = testRestTemplateRoleAdmin.exchange("/userdomains/admin/all/Pageable", HttpMethod.GET,null,
										new ParameterizedTypeReference<PageableResponse<UserDomain>>() {
										}).getBody();
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.toList().get(0)).isNotNull();
		Assertions.assertThat(userDomainEntity.toList().get(0)).isEqualTo(userDomain);
	}
	
	@Test
	@DisplayName("findById Return userDomain whenSuccessful")
	void findById_ReturnUserDomain_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		UserDomain userDomain = this.userDomainRepository.save(ADMIN);
			
		UserDomain userDomainEntity = testRestTemplateRoleAdmin.getForObject("/userdomains/admin/{id}", UserDomain.class, userDomain.getId());
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getId()).isNotNull();
		Assertions.assertThat(userDomainEntity).isEqualTo(userDomain);
	}
	
	@Test
	@DisplayName("getUser Return userDomain whenSuccessful")
	void getUser_ReturnUserDomainAuthenticated_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		UserDomain userDomain = this.userDomainRepository.save(ADMIN);
			
		UserDomain userDomainEntity = testRestTemplateRoleAdmin.getForObject("/userdomains/get-user-authenticated", UserDomain.class);
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getId()).isNotNull();
		Assertions.assertThat(userDomainEntity).isEqualTo(userDomain);
	}
	
	@Test
	@DisplayName("save return userDomain admin whenSuccessful")
	void save_ReturnUserDomainAdmin_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
	
		UserDomainPostRequestBody userDomainPostRequestBody = new UserDomainPostRequestBody("test", "insertNewUser@", "test phone", "test", "new username");
		
		ResponseEntity<UserDomain> userDomainEntity = testRestTemplateRoleAdmin.postForEntity("/userdomains/admin", userDomainPostRequestBody,UserDomain.class);
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getBody()).isNotNull();
		Assertions.assertThat(userDomainEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(userDomainEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("save return userDomain whenSuccessful")
	void save_ReturnUserDomain_whenSuccessful() {
		this.roleModelRepository.saveAll(Arrays.asList(ROLE_ADMIN,ROLE_USER));
		
		UserDomainPostRequestBody userDomainPostRequestBody = new UserDomainPostRequestBody("test", "insertNewUser@", "test phone", "test", "new username");
		
		ResponseEntity<UserDomain> userDomainEntity = testRestTemplateWithNonRoles.postForEntity("/userdomains/saveuserdomain", userDomainPostRequestBody,UserDomain.class);
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getBody()).isNotNull();
		Assertions.assertThat(userDomainEntity.getBody().getId()).isNotNull();
		Assertions.assertThat(userDomainEntity.getBody().getUsername()).isEqualTo(userDomainPostRequestBody.getUsername());
		Assertions.assertThat(userDomainEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_RemovesUserDomain_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
		
		UserDomain userDomainToBeDeleted = this.userDomainRepository.save(new UserDomain(null, "user to deleted test", "userdeleted@", "test phone", "test", "deleted user test"));
	
		ResponseEntity<Void> userDomainEntity = testRestTemplateRoleAdmin.exchange("/userdomains/admin/{id}", HttpMethod.DELETE, null, Void.class,
				userDomainToBeDeleted.getId());		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_ReplaceUserDomain_whenSuccessful() {
		this.roleModelRepository.save(ROLE_ADMIN);
		this.userDomainRepository.save(ADMIN);
	
		UserDomainPutRequestBody userDomainPut = UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator();
			
		ResponseEntity<Void> userDomainEntity = testRestTemplateRoleAdmin.exchange("/userdomains", HttpMethod.PUT, 
				new HttpEntity<>(userDomainPut), Void.class);
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("updateAnyUserDomain replace userDomain whenSuccessful")
	void updateAnyUserDomain_ReplaceUserDomain_whenSuccessful() {
		this.roleModelRepository.saveAll(Arrays.asList(ROLE_ADMIN,ROLE_USER));
		this.userDomainRepository.saveAll(Arrays.asList( ADMIN,USER));
	
		UserDomainPutRequestBody userDomainPut = UserDomainPutRequestBodyCreator.createUserDomainPutRequestBodyCreator();
			
		ResponseEntity<Void> userDomainEntity = testRestTemplateRoleAdmin.exchange("/userdomains/admin/update-full/{id}", HttpMethod.PUT, 
				new HttpEntity<>(userDomainPut), Void.class,1);
		
		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);			
	}
	
	@Test
	@DisplayName("delete returns 403 when user is not admin") 
	void delete_Returns403_WhenUserIsNotAdmin() {
		this.roleModelRepository.saveAll(Arrays.asList(ROLE_ADMIN,ROLE_USER));
		this.userDomainRepository.save(USER);

		ResponseEntity<Void> productEntity = testRestTemplateRoleUser.exchange("/userdomains/admin/{id}", HttpMethod.DELETE, null, Void.class,
				1);		

        Assertions.assertThat(productEntity).isNotNull();
		
		Assertions.assertThat(productEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.FORBIDDEN);	
	}
	
	
}
