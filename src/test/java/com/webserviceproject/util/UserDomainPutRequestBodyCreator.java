package com.webserviceproject.util;

import com.webserviceproject.request.UserDomainPutRequestBody;

public class UserDomainPutRequestBodyCreator {
	
	public static UserDomainPutRequestBody createUserDomainPutRequestBodyCreator() {
		return new UserDomainPutRequestBody("name test 2","email@test 2","phone test 2","password test 2","username test 2");
	}
}
