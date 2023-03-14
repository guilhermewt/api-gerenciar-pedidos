package com.webserviceproject.repository;

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

import com.webserviceproject.entities.Category;
import com.webserviceproject.util.CategoryCreator;

@DataJpaTest
@DisplayName("test for category repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	@Test
	@DisplayName("find all category by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Category categoryToBeSaved = categoryRepository.save(CategoryCreator.createCategory());
		Page<Category> categorySaved = this.categoryRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(categorySaved).isNotNull().isNotEmpty();
		Assertions.assertThat(categorySaved.toList().get(0)).isEqualTo(categoryToBeSaved);
	}
	
	@Test
	@DisplayName("findall return all category whenSuccessful")
	void findAll_returnAllCategory_whenSuccessful() {
		Category categoryToBeSaved = categoryRepository.save(CategoryCreator.createCategory());
		List<Category> categoryList = this.categoryRepository.findAll();
		
		Assertions.assertThat(categoryList).isNotNull();
		Assertions.assertThat(categoryList.get(0).getId()).isNotNull();
		Assertions.assertThat(categoryList.get(0)).isEqualTo(categoryToBeSaved);
	}
	
	@Test
	@DisplayName("findById return category whenSuccessful")
	void findByid_returncategory_whenSuccessful() {			
		Category categoryToBeSaved = categoryRepository.save(CategoryCreator.createCategory());
		Category categorySaved = this.categoryRepository.findById(categoryToBeSaved.getId()).get();
		
		Assertions.assertThat(categorySaved).isNotNull();
		Assertions.assertThat(categorySaved.getId()).isNotNull();
		Assertions.assertThat(categorySaved).isEqualTo(categoryToBeSaved);
	}
			
	@Test
	@DisplayName("save return category whenSuccessful")
	void save_returncategory_whenSuccessful() {	
		Category categoryToBeSaved = categoryRepository.save(CategoryCreator.createCategory());
		Category categorySaved = this.categoryRepository.save(categoryToBeSaved);
		
		Assertions.assertThat(categorySaved).isNotNull();
		Assertions.assertThat(categorySaved.getId()).isNotNull();
		Assertions.assertThat(categorySaved).isEqualTo(categoryToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes category whenSuccessful")
	void delete_removescategory_whenSuccessful() {
		Category categoryToBeSaved = categoryRepository.save(CategoryCreator.createCategory());
	
	    this.categoryRepository.delete(categoryToBeSaved);
	    
	    Optional<Category> categoryDeleted = this.categoryRepository.findById(categoryToBeSaved.getId());
	    
	    Assertions.assertThat(categoryDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace category whenSuccessful")
	void update_replaceCategory_whenSuccessful() {
		this.categoryRepository.save(CategoryCreator.createCategory());
			
		Category categoryToBeUpdate = CategoryCreator.createCategoryToBeUpdateCreator();
	
	    Category categoryUpdated = this.categoryRepository.save(categoryToBeUpdate);
	    
	    Assertions.assertThat(categoryUpdated).isNotNull();
	    Assertions.assertThat(categoryUpdated.getId()).isNotNull();
	    Assertions.assertThat(categoryUpdated).isEqualTo(categoryToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when category name is empty")
	void save_throwConstrationViolationException_whenCategoryNameIsEmpty() {
	
		Category category = new Category();
		
		Assertions.assertThatThrownBy(() -> this.categoryRepository.save(category))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
