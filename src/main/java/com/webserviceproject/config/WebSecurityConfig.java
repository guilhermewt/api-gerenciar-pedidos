package com.webserviceproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/userdomains/admin/**").hasRole("ADMIN")
		.antMatchers("/categories/admin/**").hasRole("ADMIN")
		.antMatchers("/products/admin/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/userdomains/saveuserdomain").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.headers().frameOptions().sameOrigin() 
		.and()
		.httpBasic();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
