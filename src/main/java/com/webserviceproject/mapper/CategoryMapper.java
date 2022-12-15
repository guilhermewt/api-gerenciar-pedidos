package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.Category;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
	
	public static CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	public abstract Category toCategory(CategoryPostRequestBody categoryPostRequestBody);
	
	public abstract Category updateCategory(CategoryPutRequestBody categoryPutRequestBody, 
											  @MappingTarget Category category);
}
