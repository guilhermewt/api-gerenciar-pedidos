package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.webserviceproject.entities.Category;
import com.webserviceproject.entities.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductGetRequestBody {

	private Long id;
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	@NotEmpty(message = "the description cannot be empty")
	private String description;
	@NotNull(message = "the price cannot be empty")
	private Double price;
	private String imgUrl;

	private Set<Category> category = new HashSet<>();

	public ProductGetRequestBody(Long id, String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public static Page<ProductGetRequestBody> toProductGetRequestBodyPage(Page<Product> product) {
		
		Page<ProductGetRequestBody> dtoPage = product.map(new Function<Product, ProductGetRequestBody>() {
			
			@Override
			public ProductGetRequestBody apply(Product product) {
				ProductGetRequestBody dto = new ProductGetRequestBody();
				dto.setId(product.getId());
				dto.setName(product.getName());
				dto.setDescription(product.getDescription());
				dto.setPrice(product.getPrice());
				dto.setImgUrl(product.getImgUrl());
				dto.setCategory(product.getCategory());
				return dto;
			}
		});
		
		return dtoPage;
	}
}
