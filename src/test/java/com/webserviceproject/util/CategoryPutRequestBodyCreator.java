package com.webserviceproject.util;

import com.webserviceproject.request.CategoryPutRequestBody;

public class CategoryPutRequestBodyCreator {
	
	public static CategoryPutRequestBody createCategoryPutRequestBodyCreator() {
		return new CategoryPutRequestBody(1l,"name product test 2");
	}
}
