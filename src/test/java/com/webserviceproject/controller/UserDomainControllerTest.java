package com.webserviceproject.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.webserviceproject.controllers.UserDomainController;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.request.UserDomainGetRequestBody;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.services.UserDomainService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class UserDomainControllerTest {

	@InjectMocks
	private UserDomainController userDomainController;

	@Mock
	private UserDomainService userDomainServiceMock;

	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;

	@BeforeEach
	void setUp() {
		BDDMockito.when(userDomainServiceMock.findAllNonPageable())
				.thenReturn(List.of(UserDomainCreator.createUserDomainAdmin()));
		PageImpl<UserDomain> userPage = new PageImpl<>(List.of(UserDomainCreator.createUserDomainAdmin()));

		BDDMockito.when(userDomainServiceMock.findAllPageable(ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(userPage);

		BDDMockito.when(userDomainServiceMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong()))
				.thenReturn(UserDomainCreator.createUserDomainAdmin());

		BDDMockito.when(userDomainServiceMock.getMyUser()).thenReturn(UserDomainCreator.createUserDomainAdmin());

		BDDMockito
				.when(userDomainServiceMock
						.insertUserDomainWithRoleUser(ArgumentMatchers.any(UserDomainPostRequestBody.class)))
				.thenReturn(UserDomainCreator.createUserDomainAdmin());

		BDDMockito
				.when(userDomainServiceMock
						.insertUserDomainAdmin(ArgumentMatchers.any(UserDomainPostRequestBody.class)))
				.thenReturn(UserDomainCreator.createUserDomainAdmin());

		BDDMockito.doNothing().when(userDomainServiceMock).delete(ArgumentMatchers.anyLong());

		BDDMockito.when(getAuthenticatedUser.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainAdmin());

	}

	@Test
	@DisplayName("findAll return list of userDomain whenSuccessful")
	void findAll_returnListOfUserDomain_WhenSucceful() {
		UserDomainGetRequestBody userDomainSaved = UserDomainCreator.createUserGetRequestBodyCreator();

		List<UserDomainGetRequestBody> userDomainEntity = this.userDomainController.findAllNonPageable().getBody();

		Assertions.assertThat(userDomainEntity).isNotNull();
		Assertions.assertThat(userDomainEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(userDomainEntity.get(0)).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("findAll return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		Page<UserDomain> userPage = userDomainController.findAllPageable(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findById return userDomain whenSuccessful")
	void findById_ReturnUserDomain_whenSuccessful() {
		UserDomainGetRequestBody userDomainSaved = UserDomainCreator.createUserGetRequestBodyCreator();

		UserDomainGetRequestBody userDomain = this.userDomainController.findById(userDomainSaved.getId()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("findById return userDomain authenticated whenSuccessful")
	void findUserAuthenticated_ReturnUserDomain_whenSuccessful() {
		UserDomainGetRequestBody userDomainSaved = UserDomainCreator.createUserGetRequestBodyCreator();

		UserDomainGetRequestBody userDomain = this.userDomainController.findById(userDomainSaved.getId()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save return user with role_user whenSuccessful")
	void save_ReturnUser_whenSuccessful() {
		UserDomainGetRequestBody userDomainSaved = UserDomainCreator.createUserGetRequestBodyCreator();

		UserDomainGetRequestBody userDomain = this.userDomainController.insertUser(UserDomainCreator
				.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("save return user with role_admin whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		UserDomainGetRequestBody userDomainSaved = UserDomainCreator.createUserGetRequestBodyCreator();

		UserDomainGetRequestBody userDomain = this.userDomainController.insertUserAdmin(UserDomainCreator.createUserPostRequestBodyCreator()).getBody();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_RemovesUserDomain_whenSuccessful() {
		Assertions.assertThatCode(() -> this.userDomainController.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_ReplaveUserDomain_whenSuccessful() {
		Assertions.assertThatCode(
				() -> this.userDomainController.update(UserDomainCreator.createUserDomainPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
