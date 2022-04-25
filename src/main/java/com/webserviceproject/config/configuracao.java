package com.webserviceproject.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.entities.Usuario;
import com.webserviceproject.repository.PedidoRepositorio;
import com.webserviceproject.repository.UsuarioRepositorio;

@Configuration
public class configuracao implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private PedidoRepositorio pedidoreposi;
	
	@Override
	public void run(String... args) throws Exception {
		Usuario user1 = new Usuario(null,"guilherme", "gui@gmail", "3332112", "1234");
		Usuario user2 = new Usuario(null,"welliston", "welliston@gmail", "737423", "98123");
		Usuario user3 = new Usuario(null,"marcos", "marcos@gmail", "52455", "324134");
		
		repositorio.saveAll(Arrays.asList(user1,user2,user3));
		
		Pedido ped1 = new Pedido(null, Instant.parse("2019-06-20T16:40:05Z"),user1);
		Pedido ped2 = new Pedido(null, Instant.parse("2019-04-03T16:50:04Z"),user2);
		Pedido ped3 = new Pedido(null, Instant.parse("2022-04-20T16:51:59Z"),user1);
		
		pedidoreposi.saveAll(Arrays.asList(ped1,ped2,ped3));
		
		
	}

}
