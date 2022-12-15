package com.webserviceproject.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductPutRequestBody {
	
	private long id;
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	@NotEmpty(message = "the description cannot be empty")
	private String description;
	@NotNull(message = "the price cannot be empty")
	private Double price;
	private String imgUrl;

	public ProductPutRequestBody(long id,String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
}
