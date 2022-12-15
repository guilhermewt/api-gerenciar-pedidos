package com.webserviceproject.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.webserviceproject.entities.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryPutRequestBody {
	
	private Long id;
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	
	private Set<Product> product = new HashSet<>();

	public CategoryPutRequestBody(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
