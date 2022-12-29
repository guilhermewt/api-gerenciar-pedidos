package com.webserviceproject.util;

import com.webserviceproject.entities.Product;

public class ProductCreator {
	
	public static Product createProduct() {
		Product product = new Product(1l,"name test","description test",100.0,"img test");
		return product;
	}
	
	public static Product createProductToBeUpdate() {
		Product product = new Product(1l,"name test 2","description test 2",200.0,"img test 2");
		return product;
	}
}
