package com.webserviceproject.util;

import com.webserviceproject.entities.Product;
import com.webserviceproject.request.ProductGetRequestBody;
import com.webserviceproject.request.ProductPostRequestBody;
import com.webserviceproject.request.ProductPutRequestBody;

public class ProductCreator {
	
	public static Product createProduct() {
		Product product = new Product(1l,"name test","description test",100.0,"img test");
		return product;
	}
	
	public static Product createProductToBeUpdate() {
		Product product = new Product(1l,"name test 2","description test 2",200.0,"img test 2");
		return product;
	}
	
	public static ProductPostRequestBody createProductPostRequestBodyCreator() {
		return new ProductPostRequestBody("name product test", "description product test", 100.0, "imgUrl product test");
	}
	
	public static ProductPutRequestBody createProductPutRequestBodyCreator() {
		return new ProductPutRequestBody(1l,"name product test 2", "description product test 2", 200.0, "imgUrl product test 2");
	}
	
	public static ProductGetRequestBody createProductGetRequestBodyCreator() {
		return new ProductGetRequestBody(1l,"name test","description test",100.0,"img test");
	}
}
