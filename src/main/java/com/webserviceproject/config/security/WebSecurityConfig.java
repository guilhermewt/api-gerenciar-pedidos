package com.webserviceproject.config.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webserviceproject.config.JWTConfigurer.JWTAutenticationFilter;
import com.webserviceproject.config.JWTConfigurer.JWTValidarFilter;
import com.webserviceproject.controllers.exceptions.ResourceExceptionHandler;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private ResourceExceptionHandler resourceException = new ResourceExceptionHandler();
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/userdomains/admin/**").hasRole("ADMIN")
				.antMatchers("/categories/admin/**").hasRole("ADMIN")
				.antMatchers("/products/admin/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/userdomains/saveuserdomain").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**","/","/h2-console/**").permitAll()
				.anyRequest().authenticated().and().headers().frameOptions().sameOrigin().and()
				.addFilter(new JWTValidarFilter(
						authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
				.addFilter(new JWTAutenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authException) throws IOException, ServletException {
						response.setStatus(403);
						response.setCharacterEncoding("UTF-8");
						response.setContentType(APPLICATION_JSON_VALUE);
						new ObjectMapper().writeValue(response.getOutputStream(),
								resourceException.AuthenticationException(authException));
					}
				});

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
