package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.webserviceproject.entities.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryPostRequestBody {
	
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	
	private Set<Product> product = new HashSet<>();

	public CategoryPostRequestBody(String name) {
		super();
		this.name = name;
	}
}
