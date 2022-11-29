package com.webserviceproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webserviceproject.services.UsuarioService;

import lombok.RequiredArgsConstructor;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
		
	private final UsuarioService usuarioService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/usuarios/admin/**").hasRole("ADMIN")
		.antMatchers("/categorias/admin/**").hasRole("ADMIN")
		.antMatchers("/produtos/admin/**").hasRole("ADMIN")
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.headers().frameOptions().sameOrigin() 
		.and()
		.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
	}
	
//	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
