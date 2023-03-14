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

import com.webserviceproject.entities.Product;
import com.webserviceproject.repository.ProductRepository;
import com.webserviceproject.services.CategoryService;
import com.webserviceproject.services.ProductService;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.ProductCreator;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepositoryMock;
	
	@Mock
	private CategoryService categoryRepositoryMock;

	@BeforeEach
	void setUp() {
		BDDMockito.when(productRepositoryMock.findAll()).thenReturn(List.of(ProductCreator.createProduct()));
		
		PageImpl<Product> userPage = new PageImpl<>(List.of(ProductCreator.createProduct()));
		BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(ProductCreator.createProduct()));
		
		BDDMockito.when(productRepositoryMock.save(ArgumentMatchers.any(Product.class))).thenReturn(ProductCreator.createProduct());

		BDDMockito.doNothing().when(productRepositoryMock).delete(ArgumentMatchers.any(Product.class));
		
		BDDMockito.when(categoryRepositoryMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong())).thenReturn(CategoryCreator.createCategory());

	}
	
	@Test
	@DisplayName("find all return list of product whenSuccessfull")
	void findAll_returnListOfProduct_whenSuccessful() {
		Product listProductToComparable = ProductCreator.createProduct();
		List<Product> listProduct = productService.findAllNonPageable();
		
		Assertions.assertThat(listProduct).isNotNull();
		Assertions.assertThat(listProduct.get(0).getId()).isNotNull();
		Assertions.assertThat(listProduct.get(0)).isEqualTo(listProductToComparable);	
	}
	
	@Test
	@DisplayName("find all return list of object inside page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Page<Product> userPage = productService.findAllPageable(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(productSaved);
	}
	
	@Test
	@DisplayName("findById return product whenSuccessful")
	void findById_ReturnLivro_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Product product = this.productService.findByIdOrElseThrowBadRequestException(productSaved.getId());

		Assertions.assertThat(product).isNotNull();
		Assertions.assertThat(product.getId()).isNotNull();
		Assertions.assertThat(product).isEqualTo(productSaved);
	}

	
	@Test
	@DisplayName("save  return product whenSuccessful")
	void save_ReturnProduct_whenSuccessful() {
		Product productSaved = ProductCreator.createProduct();

		Product product = this.productService
				.save(ProductCreator.createProductPostRequestBodyCreator(),1l);

		Assertions.assertThat(product).isNotNull();
		Assertions.assertThat(product.getId()).isNotNull();
		Assertions.assertThat(product).isEqualTo(productSaved);
	}
	
	@Test
	@DisplayName("delete Removes product whenSuccessful")
	void delete_RemovesLivro_whenSuccessful() {
		Assertions.assertThatCode(() -> this.productService.deleteProduct(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update Replace product whenSuccessful")
	void update_ReplaveLivro_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.productService.update(ProductCreator.createProductPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
