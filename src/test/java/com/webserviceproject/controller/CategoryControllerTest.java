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

import com.webserviceproject.controllers.CategoryController;
import com.webserviceproject.entities.Category;
import com.webserviceproject.request.CategoryGetRequestBody;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.services.CategoryService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.CategoryCreator;

@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryServiceMock;

	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;

	@BeforeEach
	void setUp() {
		BDDMockito.when(categoryServiceMock.findAllNonPageable())
				.thenReturn(List.of(CategoryCreator.createCategory()));
		PageImpl<Category> userPage = new PageImpl<>(List.of(CategoryCreator.createCategory()));

		BDDMockito.when(categoryServiceMock.findAllPageable(ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(userPage);

		BDDMockito.when(categoryServiceMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong()))
				.thenReturn(CategoryCreator.createCategory());

		BDDMockito
				.when(categoryServiceMock
						.insert(ArgumentMatchers.any(CategoryPostRequestBody.class)))
				.thenReturn(CategoryCreator.createCategory());

		BDDMockito.doNothing().when(categoryServiceMock).deleteCategory(ArgumentMatchers.anyLong());
		
		BDDMockito.when(categoryServiceMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong()))
		.thenReturn(CategoryCreator.createCategory());

	}

	@Test
	@DisplayName("findAll return list of category whenSuccessful")
	void findAll_returnListOfCategory_WhenSucceful() {
		CategoryGetRequestBody categorySaved = CategoryCreator.createCategoryGetRequestBodyCreator();

		List<CategoryGetRequestBody> categoryEntity = this.categoryController.findAllNonPageable().getBody();

		Assertions.assertThat(categoryEntity).isNotNull();
		Assertions.assertThat(categoryEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(categoryEntity.get(0)).isEqualTo(categorySaved);
	}
	
	@Test
	@DisplayName("findAll return List of object inside page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		CategoryGetRequestBody categorySaved = CategoryCreator.createCategoryGetRequestBodyCreator();

		Page<CategoryGetRequestBody> userPage = categoryController.findAllPageable(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(categorySaved);
	}

	@Test
	@DisplayName("findById return category whenSuccessful")
	void findById_ReturnCategory_whenSuccessful() {
		CategoryGetRequestBody categorySaved = CategoryCreator.createCategoryGetRequestBodyCreator();

		CategoryGetRequestBody category = this.categoryController.findById(categorySaved.getId()).getBody();

		Assertions.assertThat(category).isNotNull();
		Assertions.assertThat(category.getId()).isNotNull();
		Assertions.assertThat(category).isEqualTo(categorySaved);
	}

	@Test
	@DisplayName("save return category whenSuccessful")
	void save_ReturnCategory_whenSuccessful() {
		CategoryGetRequestBody categorySaved = CategoryCreator.createCategoryGetRequestBodyCreator();

		CategoryGetRequestBody category = this.categoryController
				.insert(CategoryCreator.createCategoryPostRequestBodyCreator()).getBody();

		Assertions.assertThat(category).isNotNull();
		Assertions.assertThat(category.getId()).isNotNull();
		Assertions.assertThat(category).isEqualTo(categorySaved);
	}

	@Test
	@DisplayName("delete removes category whenSuccessful")
	void delete_RemovesCategory_whenSuccessful() {
		Assertions.assertThatCode(() -> this.categoryController.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update replace category whenSuccessful")
	void update_ReplaveCategory_whenSuccessful() {
		Assertions.assertThatCode(
				() -> this.categoryController.update(CategoryCreator.createCategoryPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
	
}
