package com.webserviceproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.webserviceproject.config.JWTConfigurer.JWTAutenticarFilter;
import com.webserviceproject.config.JWTConfigurer.JWTValidarFilter;

@Configuration
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
		.addFilter(new JWTAutenticarFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
		.addFilter(new JWTValidarFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
}
