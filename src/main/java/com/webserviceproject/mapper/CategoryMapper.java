package com.webserviceproject.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Category;
import com.webserviceproject.request.CategoryGetRequestBody;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
	
	public static CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	public abstract Category toCategory(CategoryPostRequestBody categoryPostRequestBody);
	
	public abstract Category updateCategory(CategoryPutRequestBody categoryPutRequestBody, 											  @MappingTarget Category category);
	
	public abstract List<CategoryGetRequestBody> toCategoryGetRequestBody(List<Category> category);
	
	public abstract CategoryGetRequestBody toCategoryGetRequestBody(Category category);

}
