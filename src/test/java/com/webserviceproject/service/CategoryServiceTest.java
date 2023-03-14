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

import com.webserviceproject.entities.Category;
import com.webserviceproject.repository.CategoryRepository;
import com.webserviceproject.services.CategoryService;
import com.webserviceproject.util.CategoryCreator;


@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
	
	@InjectMocks
	private CategoryService categoryService;
	
	@Mock
	private CategoryRepository categoryRepositoryMock;


	@BeforeEach
	void setUp() {
		BDDMockito.when(categoryRepositoryMock.findAll()).thenReturn(List.of(CategoryCreator.createCategory()));
		
		PageImpl<Category> userPage = new PageImpl<>(List.of(CategoryCreator.createCategory()));
		BDDMockito.when(categoryRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(categoryRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(CategoryCreator.createCategory()));
		
		BDDMockito.when(categoryRepositoryMock.save(ArgumentMatchers.any(Category.class))).thenReturn(CategoryCreator.createCategory());

		BDDMockito.doNothing().when(categoryRepositoryMock).delete(ArgumentMatchers.any(Category.class));
		
		BDDMockito.when(categoryRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(CategoryCreator.createCategory()));

	}
	
	@Test
	@DisplayName("return list of category whenSuccessfull")
	void findAll_returnListOfCategory_whenSuccessful() {
		Category listCategoryToComparable = CategoryCreator.createCategory();
		List<Category> listCategory = categoryService.findAllNonPageable();
		
		Assertions.assertThat(listCategory).isNotNull();
		Assertions.assertThat(listCategory.get(0).getId()).isNotNull();
		Assertions.assertThat(listCategory.get(0)).isEqualTo(listCategoryToComparable);	
	}
	
	@Test
	@DisplayName("find all category return list of object inside page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Category categorySaved = CategoryCreator.createCategory();

		Page<Category> userPage = categoryService.findAllPageable(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(categorySaved);
	}
	
	@Test
	@DisplayName("findById return category whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Category categorySaved = CategoryCreator.createCategory();

		Category category = this.categoryService.findByIdOrElseThrowBadRequestException(categorySaved.getId());

		Assertions.assertThat(category).isNotNull();
		Assertions.assertThat(category.getId()).isNotNull();
		Assertions.assertThat(category).isEqualTo(categorySaved);
	}

	
	@Test
	@DisplayName("save  return category whenSuccessful")
	void save_ReturnCategory_whenSuccessful() {
		Category categorySaved = CategoryCreator.createCategory();

		Category category = this.categoryService
				.insert(CategoryCreator.createCategoryPostRequestBodyCreator());
		
		Assertions.assertThat(category).isNotNull();
		Assertions.assertThat(category.getId()).isNotNull();
		Assertions.assertThat(category).isEqualTo(categorySaved);
	}
	
	@Test
	@DisplayName("delete Removes category whenSuccessful")
	void delete_removesLivro_whenSuccessful() {
		Assertions.assertThatCode(() -> this.categoryService.deleteCategory(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update Replace category whenSuccessful")
	void update_ReplaveLivro_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.categoryService.updateCategory(CategoryCreator.createCategoryPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
