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

import com.webserviceproject.controllers.ProductController;
import com.webserviceproject.entities.Product;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.services.CategoryService;
import com.webserviceproject.services.ProductService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.ProductCreator;
import com.webserviceproject.util.ProductPostRequestBodyCreator;
import com.webserviceproject.util.ProductPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductService productServiceMock;
	
	@Mock
	private CategoryService categoryServiceMock;

	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;

	@BeforeEach
	void setUp() {
		BDDMockito.when(productServiceMock.findAllNonPageable())
				.thenReturn(List.of(ProductCreator.createProduct()));
		PageImpl<Product> userPage = new PageImpl<>(List.of(ProductCreator.createProduct()));

		BDDMockito.when(productServiceMock.findAllPageable(ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(userPage);

		BDDMockito.when(productServiceMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong()))
				.thenReturn(ProductCreator.createProduct());

		BDDMockito
				.when(productServiceMock
						.save(ArgumentMatchers.any(ProductPostRequestBody.class), ArgumentMatchers.anyLong()))
				.thenReturn(ProductCreator.createProduct());

		BDDMockito.doNothing().when(productServiceMock).deleteProduct(ArgumentMatchers.anyLong());
		
		BDDMockito.when(categoryServiceMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong()))
		.thenReturn(CategoryCreator.createCategory());

	}

	@Test
	@DisplayName("findAll return list of product whenSuccessful")
	void findAll_returnListOfProduct_WhenSucceful() {
		Product productSaved = ProductCreator.createProduct();

		List<Product> productEntity = this.productController.findAllNonPageable().getBody();

		Assertions.assertThat(productEntity).isNotNull();
		Assertions.assertThat(productEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(productEntity.get(0)).isEqualTo(productSaved);
	}
	
	@Test
	@DisplayName("findAll return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Page<Product> userPage = productController.findAllPageable(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(productSaved);
	}

	@Test
	@DisplayName("findById return category whenSuccessful")
	void findById_ReturnProduct_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Product product = this.productController.findById(productSaved.getId()).getBody();

		Assertions.assertThat(product).isNotNull();
		Assertions.assertThat(product.getId()).isNotNull();
		Assertions.assertThat(product).isEqualTo(productSaved);
	}

	@Test
	@DisplayName("save return product whenSuccessful")
	void save_ReturnProduct_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Product product = this.productController
				.save(ProductPostRequestBodyCreator.createProductPostRequestBodyCreator(),
						ProductCreator.createProduct().getId()).getBody();

		Assertions.assertThat(product).isNotNull();
		Assertions.assertThat(product.getId()).isNotNull();
		Assertions.assertThat(product).isEqualTo(productSaved);
	}

	@Test
	@DisplayName("delete removes product whenSuccessful")
	void delete_RemovesProduct_whenSuccessful() {
		Assertions.assertThatCode(() -> this.productController.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update replace product whenSuccessful")
	void update_ReplaveProduct_whenSuccessful() {
		Assertions.assertThatCode(
				() -> this.productController.update(ProductPutRequestBodyCreator.createProductPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
