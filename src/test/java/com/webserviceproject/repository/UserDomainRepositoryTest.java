package com.webserviceproject.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.util.UserDomainCreator;

@DataJpaTest
@DisplayName("test for userDomain repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDomainRepositoryTest {
	
	@Autowired
	private UserDomainRepository userDomainRepository;
	
	@Test
	@DisplayName("find all user books by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
	
		UserDomain userDomainToBeSaved = userDomainRepository.save(UserDomainCreator.createUserDomain());
		Page<UserDomain> userDomainSaved = this.userDomainRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(userDomainSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(userDomainSaved.toList().get(0)).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("findall return all userDomain whenSuccessful")
	void findAll_returnAllUserDomain_whenSuccessful() {
		UserDomain userDomainToBeSaved = userDomainRepository.save(UserDomainCreator.createUserDomain());
		List<UserDomain> userDomainList = this.userDomainRepository.findAll();
		
		Assertions.assertThat(userDomainList).isNotNull();
		Assertions.assertThat(userDomainList.get(0).getId()).isNotNull();
		Assertions.assertThat(userDomainList.get(0)).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("findById return userDomain whenSuccessful")
	void findByid_returnuserDomain_whenSuccessful() {			
		UserDomain userDomainToBeSaved = userDomainRepository.save(UserDomainCreator.createUserDomain());
		UserDomain userDomainSaved = this.userDomainRepository.findById(userDomainToBeSaved.getId()).get();
		
		Assertions.assertThat(userDomainSaved).isNotNull();
		Assertions.assertThat(userDomainSaved.getId()).isNotNull();
		Assertions.assertThat(userDomainSaved).isEqualTo(userDomainToBeSaved);
	}
			
	@Test
	@DisplayName("save return userDomain whenSuccessful")
	void save_returnuserDomain_whenSuccessful() {	
		UserDomain userDomainToBeSaved = userDomainRepository.save(UserDomainCreator.createUserDomain());
		UserDomain userDomainSaved = this.userDomainRepository.save(userDomainToBeSaved);
		
		Assertions.assertThat(userDomainSaved).isNotNull();
		Assertions.assertThat(userDomainSaved.getId()).isNotNull();
		Assertions.assertThat(userDomainSaved).isEqualTo(userDomainToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_removesuserDomain_whenSuccessful() {
		
		UserDomain userDomainToBeSaved = userDomainRepository.save(UserDomainCreator.createUserDomain());
	
	    this.userDomainRepository.delete(userDomainToBeSaved);
	    
	    Optional<UserDomain> userDomainDeleted = this.userDomainRepository.findById(userDomainToBeSaved.getId());
	    
	    Assertions.assertThat(userDomainDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_replaceuserDomain_whenSuccessful() {
		this.userDomainRepository.save(UserDomainCreator.createUserDomain());
			
		UserDomain userDomainToBeUpdate = UserDomainCreator.createUserDomainToBeUpdate();
	
	    UserDomain userDomainUpdated = this.userDomainRepository.save(userDomainToBeUpdate);
	    
	    Assertions.assertThat(userDomainUpdated).isNotNull();
	    Assertions.assertThat(userDomainUpdated.getId()).isNotNull();
	    Assertions.assertThat(userDomainUpdated).isEqualTo(userDomainToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when userDomain name is empty")
	void save_throwConstrationViolationException_whenUserDomainNameIsEmpty() {
	
		UserDomain userDomain = new UserDomain();
		
		Assertions.assertThatThrownBy(() -> this.userDomainRepository.save(userDomain))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
