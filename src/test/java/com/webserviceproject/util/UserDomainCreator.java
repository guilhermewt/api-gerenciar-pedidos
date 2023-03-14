package com.webserviceproject.util;

import java.util.List;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.request.UserDomainGetRequestBody;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;

public class UserDomainCreator {
	
	public static UserDomain createUserDomainAdmin() {
		UserDomain userDomain = new UserDomain(1l, "testName", "test@gmail", "33333333", "$2a$10$zoylFI1DPUqViPq0dA9T1./y9X.5FBdWoxP65B8G6wyXV3//4Ky9m", "username admin test");
		userDomain.setRoles(List.of(RoleModelCreator.createRoleModelADMIN()));
		return userDomain;
	}
	
	public static UserDomain createUserDomainRoleUser() {
		UserDomain userDomain = new UserDomain(2l, "testName", "test@gmail", "33333333", "$2a$10$zoylFI1DPUqViPq0dA9T1./y9X.5FBdWoxP65B8G6wyXV3//4Ky9m", "username test");
		userDomain.getRoles().add(RoleModelCreator.createRoleModelUSER());
		return userDomain;
	}
	
	public static UserDomain createUserDomainToBeUpdate() {
		return new UserDomain(1l, "testName 2", "test2@gmail", "33333333", "$2a$10$zoylFI1DPUqViPq0dA9T1./y9X.5FBdWoxP65B8G6wyXV3//4Ky9m", "username testl");
	}
	
	public static UserDomainPostRequestBody createUserPostRequestBodyCreator() {
		return new UserDomainPostRequestBody("name test","email@test","phone test","password test","username test");
	}
	
	public static UserDomainPutRequestBody createUserDomainPutRequestBodyCreator() {
		return new UserDomainPutRequestBody("testName 2", "test2@gmail", "33333333", "$2a$10$zoylFI1DPUqViPq0dA9T1./y9X.5FBdWoxP65B8G6wyXV3//4Ky9m", "username testl");
	}
	
	public static UserDomainGetRequestBody createUserGetRequestBodyCreator() {
		return new UserDomainGetRequestBody(1l, "testName", "test@gmail", "33333333", "$2a$10$zoylFI1DPUqViPq0dA9T1./y9X.5FBdWoxP65B8G6wyXV3//4Ky9m", "username admin test");
	}
	
}
