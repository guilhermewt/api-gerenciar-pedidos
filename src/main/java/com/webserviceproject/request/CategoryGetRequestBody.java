package com.webserviceproject.request;

import java.util.function.Function;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Page;

import com.webserviceproject.entities.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryGetRequestBody {

	@NotEmpty(message = "the id cannot be empty")
	private Long id;

	@NotEmpty(message = "the name cannot be empty")
	private String name;

	public CategoryGetRequestBody(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static Page<CategoryGetRequestBody> toCategoryGetRequestBody(Page<Category> category) {

		Page<CategoryGetRequestBody> dtoPage = category.map(new Function<Category, CategoryGetRequestBody>() {

			@Override
			public CategoryGetRequestBody apply(Category category) {
				CategoryGetRequestBody dto = new CategoryGetRequestBody();
				dto.setId(category.getId());
				dto.setName(category.getName());
				return dto;
			}

		});
		return dtoPage;
	}

}