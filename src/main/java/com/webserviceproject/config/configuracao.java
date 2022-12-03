//package com.webserviceproject.config;
//
//import java.time.Instant;
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//
//import com.webserviceproject.entities.Categoria;
//import com.webserviceproject.entities.ItemsDePedido;
//import com.webserviceproject.entities.Pagamento;
//import com.webserviceproject.entities.Pedido;
//import com.webserviceproject.entities.Produto;
//import com.webserviceproject.entities.RoleModel;
//import com.webserviceproject.entities.Usuario;
//import com.webserviceproject.entities.enums.OrderStatus;
//import com.webserviceproject.enums.RoleName;
//import com.webserviceproject.repository.CategoriaRepositorio;
//import com.webserviceproject.repository.ItemsDePedidoRepositorio;
//import com.webserviceproject.repository.PedidoRepositorio;
//import com.webserviceproject.repository.ProdutoRepositorio;
//import com.webserviceproject.repository.RoleModelRepositorio;
//import com.webserviceproject.repository.UsuarioRepositorio;
//
//@Configuration
//public class configuracao implements CommandLineRunner{
//
//	@Autowired
//	private UsuarioRepositorio repositorio;
//	
//	@Autowired
//	private PedidoRepositorio pedidoreposi;
//	
//	@Autowired
//	private ProdutoRepositorio produtoReposi;
//	
//	@Autowired
//	private CategoriaRepositorio categoriaReposi;
//	
//	@Autowired
//	private ItemsDePedidoRepositorio itemsRepositorio;
//	
//	@Autowired
//	private RoleModelRepositorio roleModelRepositorio;
//
//	
//	@Override
//	public void run(String... args) throws Exception {
////		RoleModel roleAdmin = new RoleModel(1l, RoleName.ROLE_ADMIN);
////		RoleModel roleUser = new RoleModel(2l, RoleName.ROLE_USER);
////		
////		roleModelRepositorio.save(roleAdmin);
////		roleModelRepositorio.save(roleUser);
//		
//		Usuario user1 = new Usuario(1l,"guilherme", "gui@gmail", "3332112", "$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y","guilhermeSilva");
//		Usuario user2 = new Usuario(null,"welliston", "welliston@gmail", "737423", "$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y","wellistonPereira");
//		Usuario user3 = new Usuario(null,"marcos", "marcos@gmail", "52455", "$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y","marcosBernado");
//		
//		
//		repositorio.saveAll(Arrays.asList(user1,user2,user3));
//		
//		Pedido ped1 = new Pedido(null, Instant.parse("2019-06-20T16:40:05Z"),OrderStatus.PAID);
//		Pedido ped2 = new Pedido(null, Instant.parse("2019-04-03T16:50:04Z"),OrderStatus.WAITING_PAYMENT);
//		Pedido ped3 = new Pedido(null, Instant.parse("2022-04-20T16:51:59Z"),OrderStatus.WAITING_PAYMENT);
//		
//		ped1.setUsuario(user1);
//		ped2.setUsuario(user2);
//		ped3.setUsuario(user1);
//		pedidoreposi.saveAll(Arrays.asList(ped1,ped2,ped3));
//		
//		
//		Pagamento pay1 = new Pagamento(null, Instant.parse("2019-06-20T16:50:05Z"), ped1);
//		ped1.setPagamento(pay1);
//		
//		pedidoreposi.save(ped1);
//		
//		Produto p1 = new Produto(null,"freira","livro terror",90.9,"");
//		Produto p2 = new Produto(null,"asus 3012","computador asus",100.9,"");
//		Produto p3 = new Produto(null,"smart tv","samsung",2010.9,"");
//		Produto p4 = new Produto(null,"geladeira","phicon",4000.9,"");
//		Produto p5 = new Produto(null,"softskills","A Menina Que Roubava Livros - Markus suzak",,"");
//
//		
//		produtoReposi.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
//		
//		
//		Categoria cat1 = new Categoria(null, "Books");
//		Categoria cat2 = new Categoria(null, "Eletronicos");
//		Categoria cat3 = new Categoria(null, "Computadores");	
//		
//		
//		categoriaReposi.saveAll(Arrays.asList(cat1,cat2,cat3));
//		
//		p1.getCategoria().add(cat1);
//		p2.getCategoria().add(cat3);
//		p3.getCategoria().add(cat2);
//		p3.getCategoria().add(cat3);
//		p4.getCategoria().add(cat2);
//		p5.getCategoria().add(cat1);
//		
//		produtoReposi.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
//		
//		ItemsDePedido items1 = new ItemsDePedido(p1, ped1, 2, p1.getPreco());
//		ItemsDePedido items2 = new ItemsDePedido(p4, ped3, 3, p4.getPreco());
//		ItemsDePedido items3 = new ItemsDePedido(p2, ped2, 5, p2.getPreco());
//		ItemsDePedido items4 = new ItemsDePedido(p3, ped1, 4, p3.getPreco());
//		
//		itemsRepositorio.saveAll(Arrays.asList(items1,items2,items3,items4));
//	}		
//}
