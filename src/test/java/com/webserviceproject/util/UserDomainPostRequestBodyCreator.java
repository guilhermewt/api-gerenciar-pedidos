package com.webserviceproject.util;

import com.webserviceproject.request.UserDomainPostRequestBody;

public class UserDomainPostRequestBodyCreator {
	
	public static UserDomainPostRequestBody createUserPostRequestBodyCreator() {
		return new UserDomainPostRequestBody("name test","email@test","phone test","password test","username test");
	}
}
