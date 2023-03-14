package com.webserviceproject.service;

import java.util.List;
import java.util.Optional;

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

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.services.UserDomainService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.RoleModelCreator;
import com.webserviceproject.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class UserDomainServiceTest {

	@InjectMocks
	private UserDomainService userDomainService;
		
	@Mock
	private UserDomainRepository userDomainRepositoryMock;
	
	@Mock
	private RoleModelRepository roleModelRepositoryMock;
	
	@Mock
	private GetAuthenticatedUser getAuthenticatedUserMock;
		
	@BeforeEach
	void setUp() {
		BDDMockito.when(userDomainRepositoryMock.findAll()).thenReturn(List.of(UserDomainCreator.createUserDomainAdmin()));
		
		PageImpl<UserDomain> userPage = new PageImpl<>(List.of(UserDomainCreator.createUserDomainAdmin()));
		BDDMockito.when(userDomainRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(userDomainRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(UserDomainCreator.createUserDomainAdmin()));
		
		BDDMockito.when(userDomainRepositoryMock.save(ArgumentMatchers.any(UserDomain.class))).thenReturn(UserDomainCreator.createUserDomainAdmin());

		BDDMockito.doNothing().when(userDomainRepositoryMock).delete(ArgumentMatchers.any(UserDomain.class));

		BDDMockito.when(getAuthenticatedUserMock.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomainAdmin());
		
		BDDMockito.when(roleModelRepositoryMock.findById(ArgumentMatchers.eq(1l))).thenReturn(Optional.of(RoleModelCreator.createRoleModelADMIN()));
		
		BDDMockito.when(roleModelRepositoryMock.findById(ArgumentMatchers.eq(2l))).thenReturn(Optional.of(RoleModelCreator.createRoleModelADMIN()));

	}
	
	@Test
	@DisplayName("find all return list of userDomain whenSuccessfull")
	void findAll_returnListOfUserDomain_whenSuccessful() {
		UserDomain listUserDomainToComparable = UserDomainCreator.createUserDomainAdmin();
		List<UserDomain> listUserDomain = userDomainService.findAllNonPageable();
		
		Assertions.assertThat(listUserDomain).isNotNull();
		Assertions.assertThat(listUserDomain.get(0).getId()).isNotNull();
		Assertions.assertThat(listUserDomain.get(0)).isEqualTo(listUserDomainToComparable);	
	}
	
	@Test
	@DisplayName("find all Return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		Page<UserDomain> userPage = userDomainService.findAllPageable(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("findById return livro whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		UserDomain userDomain = this.userDomainService.findByIdOrElseThrowBadRequestException(userDomainSaved.getId());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("findUser return user authenticated whenSuccessful")
	void findUser_ReturnUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		UserDomain userDomain = this.userDomainService.getMyUser();

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("save return userDomain with role admin whenSuccessful")
	void save_ReturnUserAdmin_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		UserDomain userDomain = this.userDomainService
				.insertUserDomainAdmin(UserDomainCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}
	
	@Test
	@DisplayName("save return userDomain with role user whenSuccessful")
	void save_ReturnUserWithRoleUser_whenSuccessful() {
		UserDomain userDomainSaved = UserDomainCreator.createUserDomainAdmin();

		UserDomain userDomain = this.userDomainService
				.insertUserDomainWithRoleUser(UserDomainCreator.createUserPostRequestBodyCreator());

		Assertions.assertThat(userDomain).isNotNull();
		Assertions.assertThat(userDomain.getId()).isNotNull();
		Assertions.assertThat(userDomain).isEqualTo(userDomainSaved);
	}

	@Test
	@DisplayName("delete removes userDomain whenSuccessful")
	void delete_RemovesUserDomain_whenSuccessful() {
		Assertions.assertThatCode(() -> this.userDomainService.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update replace userDomain whenSuccessful")
	void update_ReplaceUserDomain_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.userDomainService.update(UserDomainCreator.createUserDomainPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
