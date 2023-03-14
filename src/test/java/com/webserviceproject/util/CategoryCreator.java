package com.webserviceproject.util;

import com.webserviceproject.entities.Category;
import com.webserviceproject.request.CategoryGetRequestBody;
import com.webserviceproject.request.CategoryPostRequestBody;
import com.webserviceproject.request.CategoryPutRequestBody;

public class CategoryCreator {
	
	public static Category createCategory() {
		Category category = new Category(1l, "category name test");
		return category;
	}
	
	public static Category createCategoryToBeUpdateCreator() {
		Category category = new Category(1l, "category name test 2");
		return category;
	}
	
	public static CategoryPostRequestBody createCategoryPostRequestBodyCreator() {
		return new CategoryPostRequestBody("name category test");
	}
	
	public static CategoryPutRequestBody createCategoryPutRequestBodyCreator() {
		return new CategoryPutRequestBody(1l,"name product test 2");
	}
	
	public static CategoryGetRequestBody createCategoryGetRequestBodyCreator() {
		CategoryGetRequestBody category = new CategoryGetRequestBody(1l, "category name test");
		return category;
	}
	
	public static CategoryGetRequestBody createCategoryGetRequestToBeUpdateCreator() {
		CategoryGetRequestBody category = new CategoryGetRequestBody(1l, "category name test 2");
		return category;
	}
}
