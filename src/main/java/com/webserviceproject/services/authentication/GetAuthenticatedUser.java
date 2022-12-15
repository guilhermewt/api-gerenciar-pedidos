package com.webserviceproject.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedUser {
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	private final UserDomainRepository userDomainRepository;
	
	public UserDomain userAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return userDomainRepository.findByUsername(authentication.getName()).orElseThrow(
				() -> new BadRequestException(authentication.getName()));
	}
}
