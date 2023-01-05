package com.webserviceproject.util;

import com.webserviceproject.request.ProductPostRequestBody;

public class ProductPostRequestBodyCreator {
	
	public static ProductPostRequestBody createProductPostRequestBodyCreator() {
		return new ProductPostRequestBody("name product test", "description product test", 100.0, "imgUrl product test");
	}
}
