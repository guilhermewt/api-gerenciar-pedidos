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

import com.webserviceproject.entities.Product;
import com.webserviceproject.util.ProductCreator;

@DataJpaTest
@DisplayName("test for product repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	@DisplayName("find all product by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
	
		Product productToBeSaved = productRepository.save(ProductCreator.createProduct());
		Page<Product> productSaved = this.productRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(productSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(productSaved.toList().get(0)).isEqualTo(productToBeSaved);
	}
	
	@Test
	@DisplayName("findall return all product whenSuccessful")
	void findAll_returnAllProduct_whenSuccessful() {
		Product productToBeSaved = productRepository.save(ProductCreator.createProduct());
		List<Product> productList = this.productRepository.findAll();
		
		Assertions.assertThat(productList).isNotNull();
		Assertions.assertThat(productList.get(0).getId()).isNotNull();
		Assertions.assertThat(productList.get(0)).isEqualTo(productToBeSaved);
	}
	
	@Test
	@DisplayName("findById return product whenSuccessful")
	void findByid_returnproduct_whenSuccessful() {			
		Product productToBeSaved = productRepository.save(ProductCreator.createProduct());
		Product productSaved = this.productRepository.findById(productToBeSaved.getId()).get();
		
		Assertions.assertThat(productSaved).isNotNull();
		Assertions.assertThat(productSaved.getId()).isNotNull();
		Assertions.assertThat(productSaved).isEqualTo(productToBeSaved);
	}
			
	@Test
	@DisplayName("save return product whenSuccessful")
	void save_returnproduct_whenSuccessful() {	
		Product productToBeSaved = productRepository.save(ProductCreator.createProduct());
		Product productSaved = this.productRepository.save(productToBeSaved);
		
		Assertions.assertThat(productSaved).isNotNull();
		Assertions.assertThat(productSaved.getId()).isNotNull();
		Assertions.assertThat(productSaved).isEqualTo(productToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes product whenSuccessful")
	void delete_removesproduct_whenSuccessful() {
		
		Product productToBeSaved = productRepository.save(ProductCreator.createProduct());
	
	    this.productRepository.delete(productToBeSaved);
	    
	    Optional<Product> productDeleted = this.productRepository.findById(productToBeSaved.getId());
	    
	    Assertions.assertThat(productDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace product whenSuccessful")
	void update_replaceproduct_whenSuccessful() {
		this.productRepository.save(ProductCreator.createProduct());
			
		Product productToBeUpdate = ProductCreator.createProductToBeUpdate();
	
	    Product productUpdated = this.productRepository.save(productToBeUpdate);
	    
	    Assertions.assertThat(productUpdated).isNotNull();
	    Assertions.assertThat(productUpdated.getId()).isNotNull();
	    Assertions.assertThat(productUpdated).isEqualTo(productToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when product name is empty")
	void save_throwConstrationViolationException_whenProductNameIsEmpty() {
	
		Product product = new Product();
		
		Assertions.assertThatThrownBy(() -> this.productRepository.save(product))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
