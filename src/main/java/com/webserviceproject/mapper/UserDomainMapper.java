package com.webserviceproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class UserDomainMapper {
	
	public static UserDomainMapper INSTANCE = Mappers.getMapper(UserDomainMapper.class);
	
	public abstract UserDomain toUserDomain(UserDomainPostRequestBody userDomainPostRequestBody); 
	
	public abstract UserDomain updateUserDomain(UserDomainPutRequestBody userDomainPutRequestBody,
										  @MappingTarget UserDomain userDomin);	
}
