package com.webserviceproject.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.repository.UsuarioRepositorio;

@Configuration
public class configuracao implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Override
	public void run(String... args) throws Exception {
		Usuario user1 = new Usuario(null,"guilherme", "gui@gmail", "3332112", "1234");
		Usuario user2 = new Usuario(null,"welliston", "welliston@gmail", "737423", "98123");
		Usuario user3 = new Usuario(null,"marcos", "marcos@gmail", "52455", "324134");
		
		repositorio.saveAll(Arrays.asList(user1,user2,user3));
		
	}

}
