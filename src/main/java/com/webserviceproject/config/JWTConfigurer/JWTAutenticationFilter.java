package com.webserviceproject.config.JWTConfigurer;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.webserviceproject.data.JwtObject;
import com.webserviceproject.data.UserDomainDetails;
import com.webserviceproject.request.LoginGetRequestBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JWTAutenticationFilter {

	public static final int TOKEN_EXPIRATION = 300_000;
	public static final String TOKEN_PASSWORD = "463408a1-54c9-4307-bb1c-6cced559f5a7";

	private final AuthenticationManager authenticationManager;

	public JwtObject attemptAuthentication(LoginGetRequestBody login) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword(), new ArrayList<>()));
		UserDomainDetails userDomainData = (UserDomainDetails) authentication.getPrincipal();
		return createToken(userDomainData);
	}

	public JwtObject createToken(UserDomainDetails userDomainData) {

		String token = JWT.create().withSubject(userDomainData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
				.withClaim("roles", userDomainData.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.sign(Algorithm.HMAC512(TOKEN_PASSWORD));

		return new JwtObject(userDomainData.getUsername(), token);
	}

}
