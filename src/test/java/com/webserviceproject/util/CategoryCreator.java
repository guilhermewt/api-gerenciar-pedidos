package com.webserviceproject.util;

import com.webserviceproject.entities.Category;

public class CategoryCreator {
	
	public static Category createCategory() {
		Category category = new Category(1l, "category name test");
		return category;
	}
	
	public static Category createCategoryToBeUpdate() {
		Category category = new Category(1l, "category name test 2");
		return category;
	}
}
