package com.webserviceproject.util;

import com.webserviceproject.request.ProductPutRequestBody;

public class ProductPutRequestBodyCreator {
	
	public static ProductPutRequestBody createProductPutRequestBodyCreator() {
		return new ProductPutRequestBody(1l,"name product test 2", "description product test 2", 200.0, "imgUrl product test 2");
	}
}
