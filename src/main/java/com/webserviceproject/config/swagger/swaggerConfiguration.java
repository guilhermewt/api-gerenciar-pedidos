package com.webserviceproject.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class swaggerConfiguration {
	
	@Bean
	  public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("order Manager API")
	              .description(description())
	              .contact(contact())
	              .license(new License().name("Apache License Version 2.0").url("http://springdoc.org")))
	           
	              .externalDocs(new ExternalDocumentation()
	              .description("Github project Documentation")
	              .url("https://github.com/guilhermewt/order-manager"))
	              .components(new Components().addSecuritySchemes("bearer-key", 
	            		  new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	  }
	
	public Contact contact() {
		return new Contact()
				.name("Guilherme Silva")
				.url("https://github.com/guilhermewt")
				.email("welliston10@gmail.com");
	}
	
	public String  description() {
		return "Uma API REST que faz o gerenciamento de pedidos. A funcionalidade dela gira em torno da criação de pedidos, recebendo um produto e sua quantidade. Então ela processará as informaçoẽs fazendo todas associaçoẽs , calculando o total e por fim retornando um Json  com todas informaçoẽs do pedido e seu estado de pagamento."
				+ "OBS:Para usar qualquer um dos métodos tem que se cadastrar no metodo (post - userdomains/saveuserdomains)";
	}
	
}
