package com.webserviceproject.util;

import com.webserviceproject.entities.UserDomain;

public class UserDomainCreator {
	
	public static UserDomain createUserDomain() {
		UserDomain userDomain = new UserDomain(1l, "testName", "test@gmail", "33333333", "test", "test");
		return userDomain;
	}
	
	public static UserDomain createUserDomainToBeUpdate() {
		UserDomain userDomain = new UserDomain(1l, "testName 2", "test2@gmail", "33333333", "test 2", "test 2");
		return userDomain;
	}
}
