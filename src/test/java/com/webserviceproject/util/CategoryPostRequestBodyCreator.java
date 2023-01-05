package com.webserviceproject.util;

import com.webserviceproject.request.CategoryPostRequestBody;

public class CategoryPostRequestBodyCreator {
	
	public static CategoryPostRequestBody createCategoryPostRequestBodyCreator() {
		return new CategoryPostRequestBody("name category test");
	}
}
