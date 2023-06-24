<div align="center">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/spring-boot.webp" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/java.webp" style="width:70px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/jwt.png" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/postgre.jpg" style="width:80px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/docker.jpg" style="width:90px;height:50px;">
	<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/JUnit.svg" style="width:90px;height:50px;">
</div>


# API de pedidos

A API Gerenciadora de Pedidos é um projeto desenvolvido em Java utilizando o framework Spring Boot. Seu objetivo é disponibilizar endpoints para o controle de pedidos, permitindo a criação, consulta e atualização de pedidos.

Com a API, é possível realizar a criação de pedidos com informações detalhadas sobre os produtos e suas quantidades. Além disso, a API calcula automaticamente o preço total do pedido, facilitando o processo de controle financeiro.

Através dos endpoints disponibilizados, os usuários podem consultar as informações de um pedido específico, obtendo detalhes sobre os produtos, quantidades, preço total e situação do pagamento. Além disso, é possível atualizar a situação do pagamento de um pedido, mantendo o status atualizado durante todo o processo.

Portanto, o projeto resolve o problema de controle e gerenciamento de pedidos de forma automatizada, proporcionando uma solução eficiente para empresas que lidam com a gestão de pedidos em suas operações diárias.


## Tabela de conteúdos

- [Arquitetura](#arquitetura)
- [Instruçoẽs de execução](#Instruçoẽs-de-execução)
- [Como usar a Api](#Como-usar-a-Api)
- [Endpoints da Api](#Endpoints-da-Api)

# Arquitetura

### UML do backend
<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/uml-api-pedidos.png">

### UML do banco de dados
<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/uml-BD-api-pedidos.png">

### Spring Boot
É o backend principal da minha aplicação, feita em java somente pelo motivo de eu já conhecer a linguagem e ter experiência com o ecossistema spring, partir pra esse lado me deu muita produtivdade na hora de codificar os endpoints da API.

### Banco de dados
Escolhir o banco de dados relacional postgreSql por ser um banco que confio e ele é bastante estável e claro por ser meu banco de dados principal
<img banco de dados

### segurança
De inicio a segurança era feita apenas com o spring security 'basic auth', depois foi adicionada uma baseada em JTW (json web token)

### Testabilidade 
    - testes de jpa para testar a camada de repository inclusive porque temos algumas querys personalizadas;
    - Os testes unitários foram baseados em Junit junto com seus @injectMocks e @Mock
    - Testes Automatizados tentando simular um ambiente em producao o máximo possível

### Dependências
	- java-jwt: biblioteca para criar os tokens
	- springdoc-openapi-data-rest,springdoc-openapi-security,springdoc-openapi-ui: dependências para construir a documentacao em swagger
	- spring-boot-starter-validation: dependencia para validar os objetos
	- mapstruct: esta biblioteca facilitar na conversao de dtos para objetos trazendo metodos prontos
	- lombok: O Lombok é uma biblioteca para Java que ajuda a reduzir a quantidade de código repetitivo necessário para escrever classes, principalmente em relação à criação de getters, setters, construtores e métodos equals, hashCode e toString.
	- spring boot starter data jpa: Ela facilita o acesso a bancos de dados relacionais utilizando a Java Persistence API (JPA). Ele fornece um conjunto de abstrações e utilitários que simplificam o desenvolvimento de aplicações que interagem com bancos de dados por meio de mapeamento objeto-relacional.
	- spring boot starter web: ela facilita a criação de aplicativos web prontos para uso, permitindo que você desenvolva rapidamente APIs RESTful e aplicativos da web inclusive ja traz um servidor apache.
	- spring-boot-devtools: uma ferramenta de reinicilizacao automatica
	- h2database : banco de dados em memoria é usado principalmente nos testes
	- spring-boot-starter-test: dependencia para rodar testes na aplicacao
	- spring-boot-starter-security: ela é responsável pela seguranca da aplicação

 ### relacionamentos

<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/order-tem.png">

-  No relaciomento entre produto e pedido eu terei atributos extras representando a quantidade de produtos, preco de cada produto e o subtotal que nada mais é do que (quantidade x preco), isso ocorre para que as informacoes do pedido sejam salva naquele momento caso um produto tenha seu preco modificado futuramente.
Mas aqui tem um problema, no banco de dados eu posso simplesmente criar uma tabela de order item fazendo a identificacao por meio de uma chave de produto e uma de pedido, sendo assim identifico o produto e de qual pedido ele pertence e termino de criar os outros atributos (qtd,preco...) segue a imagem como exemplo:
<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/tabela-order-item.png">

-  mas como isso ficaria no backend? já que na orientacao a objetos nao existe chave composta. Eu terei que criar uma classe auxiliar que irá identificar o par produto e pedido ja que a classe order item nao tem um identificador unico e sim o par ligando o pedido e produto.Então eu terei a classe 'order_item' que vai representar a associacao entre pedidos e produtos. Terei tambem uma classe chamada orderItemPK e esta classe sera responsavel pelas chaves compostas. segue a imagem de exemplo:
  <img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/order-item-pk.png">


 # Endpoints da Api

 ## Tabela de conteúdos

- [Endpoints para manipular informaçoẽs dos usuários](#Endpoints-para-manipular-informaçoẽs-dos-usuários)
- [Endpoints para manipular informaçoẽs de pedidos](#Endpoints-para-manipular-informaçoẽs-de-pedidos)
- [Endpoints para manipular informaçoẽs dos produtos](#Endpoints-para-manipular-informaçoẽs-dos-produtos)
- [Endpoints para manipular informaçoẽs das categorias dos produtos](#Endpoints-para-manipular-informaçoẽs-das-categorias-dos-produtos)
- [Endpoints para manipular informaçoẽs dos pagamentos de pedidos](#Endpoints-para-manipular-informaçoẽs-dos-pagamentos-de-pedidos)

 
 ## Endpoints para manipular informaçoẽs dos usuários
 #### cadastrar usuário
 Nesta rota qualquer usuários podem se cadastrar seguindo o json abaixo:
 ### 
       POST - http://localhost:8080/userdomains/saveuserdomain
 ###     
     {
	    "name": "nome do usuario",
	    "email": "email do usuario",
	    "phone": "celular do usuario",
	    "password": "senha do usuario",
	    "username": "nome de login do usuario"
      }

 #### Buscar informaçoẽs do usuário
 End point para o usuário poder visualizar seus dados:
 ### 
       GET - http://localhost:8080/userdomains/get-user-authenticated
 ###     
     {
	    "name": "nome do usuario",
	    "email": "email do usuario",
	    "phone": "celular do usuario",
	    "username": "nome de login do usuario"
      }
 
 #### Atualizar informaçoẽs do usuário
 End point para o usuário atualizar seus dados:
 ### 
       PUT - http://localhost:8080/userdomains
 ###     
     {
	    "name": "nome do usuario",
  	    "email": "email do usuario",
  	    "phone": "celular do usuario",
  	    "password": "senha do usuario",
  	    "username": "nome de login do usuario"
      }

 * OS SEGUINTES END POINTS SÓ TERÃO ACESSO USUÁRIOS ADMINISTRADORES
 
 #### Cadastrar usuário administrador
 Endpoint para usuário administrador possa cadastrar um outro usuário administrador
 ### 
       POST - http://localhost:8080/userdomains/admin
 ###     
     {
	    "name": "nome do usuario",
  	    "email": "email do usuario",
  	    "phone": "celular do usuario",
  	    "password": "senha do usuario",
  	    "username": "nome de login do usuario"
      }      
 
 #### Listar todos usuários
 Endpoint para listar todos usuários 
 ### 
       GET - http://localhost:8080/userdomains/admin/all/
 ### 
     [
	     {
		    "name": "nome do usuario",
	  	    "email": "email do usuario",
	  	    "phone": "celular do usuario",
	  	    "password": "senha do usuario",
	  	    "username": "nome de login do usuario"
	      }
      ]

 #### Listar todos usuários paginados
 Endpoint para listar todos usuários com paginação 
 ### 
       GET - http://localhost:8080/userdomains/admin/all/Pageable&size=20&page=2f
 ### 
     {
	content:[
		   {
			"name": "nome do usuario",
			"email": "email do usuario",
			"phone": "celular do usuario",
			"password": "senha do usuario",
			"username": "nome de login do usuario"
		    }
		 ]
      }

  #### Buscar usuários por id
 Endpoint para usuário administrador buscar um usuário por id
 ### 
       GET - http://localhost:8080/userdomains/admin/{id do usuario}
 ###     
     {
	    "name": "nome do usuario",
  	    "email": "email do usuario",
  	    "phone": "celular do usuario",
  	    "username": "nome de login do usuario"
      }

 #### Atualizar usuário
 Endpoint para usuário administrador atualizar os dados de um outro usuario
 ### 
       PUT - http://localhost:8080/admin/update-full/{id do usuario que sera atualizado}
 ###     
     {
	    "name": "nome do usuario",
  	    "email": "email do usuario",
  	    "phone": "celular do usuario",
  	    "username": "nome de login do usuario"
      }
 
 #### Deletar um usuário

 ### 
       DELETE - http://localhost:8080/admin/update-full/{id do usuario que sera atualizado}

 ## Endpoints para manipular informaçoẽs de pedidos

 #### Realizar pedidos
 
 * Para criar um pedido é necessario o id do produto e a quantidade.      
 * A data (moment) dever estar neste formato
 * Escolhe um dos status do pedido(orderStatus): WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED
 ### 
       POST - http://localhost:8080/orders/{id do produto}/{quantidade do produto}     
 ###     
     {
	    "moment": "2023-06-22T20:15:52.641Z",
  	    "orderStatus": "WAITING_PAYMENT"
      }

 
 #### Listar todos pedidos do usuário

 ### 
       GET - http://localhost:8080/orders    
 ###     
     [
	    {
		    "id": 0,
		    "moment": "2023-06-22T20:25:19.436Z",
		    "orderStatus": "WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED",
		    "items": [
		      {
		        "quantity": 0,
		        "price": 0,
		        "product": {
			          "id": 0,
			          "name": "nome do produto",
			          "description": "descricao do produto",
			          "price": 0,
			          "imgUrl": "string",
			          "category": [
				            {
				              "id": 0,
				              "name": "nome da categoria do pedido"
				            }
			          ]
		          },
		          "subTotal": 0
	                }
	              ]
	    }
      ]    

 #### Listar todos pedidos do usuário paginados
 * Listar todos os pedidos do usuário logado com paginação
 * o padrão é tamanho 20 e página 0
 ### 
       GET - http://localhost:8080/orders/Pageable&size=20&page=2f    
 ###     
      {
	 content:[
		    {
			    "id": 0,
			    "moment": "2023-06-22T20:25:19.436Z",
			    "orderStatus": "WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED",
			    "items": [
			      {
			        "quantity": 0,
			        "price": 0,
			        "product": {
				          "id": 0,
				          "name": "nome do produto",
				          "description": "descricao do produto",
				          "price": 0,
				          "imgUrl": "string",
				          "category": [
					            {
					              "id": 0,
					              "name": "nome da categoria do pedido"
					            }
				          ]
			          },
			          "subTotal": 0
		                }
		              ]
		    }
	      ]
      }
 
 #### Buscar pedidos por Id
 
 ### 
       GET - http://localhost:8080/orders/{id do pedido}
 ### 
     {
		  "id": 0,
		  "moment": "2023-06-22T20:27:03.023Z",
		  "orderStatus": "WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED",
		  "items": [
		      {
		        "quantity": 0,
		        "price": 0,
		        "product": {
		          "id": 0,
		          "name": "nome do produto",
		          "description": "descricao do produto",
		          "price": 0,
		          "imgUrl": "string",
		          "category": [
		            {
		              "id": 0,
		              "name": "nome da categoria do pedido"
		            }
		          ]
		      },
		      "subTotal": 0
		    }
		  ]
	
     }
 ### Atualizar pedidos
 * esta rota pode ser usada para atualizar o status do pedido a cada etapa da venda.
 * Coloque o id do pedido
 * selecione um desses status: WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED

 ### 
       PUT - http://localhost:8080/orders/{id}
 ###     
     {
	    "id": 1,
  	    "moment": "2023-06-22T20:29:15.864Z",
  	    "orderStatus": "PAID"
      }      

 #### Atualizar pedidos
 
 ### 
       DELETE - http://localhost:8080/orders/{id do pedido}

 ## Endpoints para manipular informaçoẽs dos produtos
 
 ### Listar todos Produtos paginados

 ### 
       GET - http://localhost:8080/products/all/pageable   
 ###     
     {
	 content:[
		    {
			"id": 0,
			"name": "nome do produto",
			"description": "descricao do produto",
			"price": 0,
			"imgUrl": "string",
			"category": [
			{
				"id": 0,
				"name": "nome da categoria"
			}
			]
            	     }   
      }
 
     

 ### Listar todos Produtos

 ### 
       GET - http://localhost:8080/products/all  
 ###
     [
	    {
		"id": 0,
    		"name": "nome do produto",
    		"description": "descricao do produto",
    		"price": 0,
    		"imgUrl": "string",
    		"category": [
      			{
        			"id": 0,
        			"name": "nome da categoria"
      			}
    		]
	    }
      ]

 ### Busca produtos pelo id

 ### 
       GET - http://localhost:8080/products/{id} 
 ###
     {
	"id": 0,
	"name": "nome do produto",
	"description": "descricao do produto",
	"price": 0,
	"imgUrl": "string",
	"category": [
	      {
	        "id": 0,
	        "name": "nome da categoria"
	      }
	]
      }

 * OS SEGUINTES END POINTS SÓ TERÃO ACESSO USUÁRIOS ADMINISTRADORES

 ### Busca produtos pelo id
 * salvar um novo produto. É necessário enviar o produto e o id da categoria do dele
 ### 
     POST - http://localhost:8080/products/admin/{categoryId} 
 ###
     {
	"id": 0,
    	"name": "nome do produto",
    	"description": "descricao do produto",
    	"price": 0,
    	"imgUrl": "string"
      }

 ### Atualizar um produto
 * salvar um novo produto. É necessário enviar o produto e o id da categoria do dele
 ### 
     PUT - http://localhost:8080/products/admin/
 ###
     {
    	"id": 1,
    	"name": "nome do produto",
    	"description": "descricao do produto",
    	"price": 0,
    	"imgUrl": "string"
      }

 ### Deletar um produto
 * deletar um produto (só é possível deletar um produto caso ele não tenha nenhuma associação com nenheum pedido)
 ### 
     DELETE - http://localhost:8080/products/admin/{id}

## Endpoints para manipular informaçoẽs das categorias dos produtos

### Listar todas categorias paginadas
    GET - http://localhost:8080/products/Pageable&size=20&page=2f
###
    {
      "content": [
	    {
	      "id": 1,
	      "name": "eletronicos"
	    }
       ]
     }

### Listar todas categorias
    GET - http://localhost:8080/categories/all
###
    [
  	{
    	  "id": 0,
          "name": "nome da categoria"
         }
    ]

### Buscar categorias pelo Id 
    GET - http://localhost:8080/categories/{id}
###
    {
      "id": 1,
      "name": "nome da categoria"
    }

* OS SEGUINTES END POINTS SÓ TERÃO ACESSO USUÁRIOS ADMINISTRADORES    

### Cadastrar categoria 
    POST - http://localhost:8080/categories/admin
###
    {
      "id": 1,
      "name": "nome da categoria"
    }

### Atualizar categoria 
    PUT - http://localhost:8080/categories/admin
###
    {
      "id": 1,
      "name": "nome da categoria"
     }

### Deletar categoria 
 Deletar uma categoria (só é possível deletar um categoria caso ela não tenha nenhuma associação)
### 
    DELETE - http://localhost:8080/categories/admin/{id da categoria}

## Endpoints para manipular informaçoẽs dos pagamentos de pedidos

### Buscar o pagamento do pedido
    GET - http://localhost:8080/payment/findOrderId/{id do pedido}
###

### Realizar o pagamento
    POST - http://localhost:8080/payment/{id do pedido}
###
    {
      "moment": "2023-06-23T14:04:29.308Z"
     }

# Instruçoẽs de execução 

1 - Certifique-se de ter o ambiente de desenvolvimento configurado:
- Instale o Java Development Kit (JDK) em sua máquina. Recomenda-se o JDK 8 ou superior.
- Verifique se o Java está configurado corretamente nas variáveis de ambiente do seu sistema operacional.

2 - Baixe ou clone o projeto da API Spring Boot
- Você pode obter o código-fonte da API Spring Boot de diferentes fontes, como um repositório Git ou um arquivo compactado (ZIP).

3 - Importe o projeto na sua IDE de desenvolvimento
- Abra sua IDE preferida (Eclipse, IntelliJ, NetBeans, etc.).
- Importe o projeto da API Spring Boot na IDE seguindo as instruções específicas da sua IDE. Isso geralmente envolve a importação do projeto como um projeto Maven ou Gradle, dependendo do seu sistema de construção.

4 - Certifique-se de que as dependências do projeto sejam resolvidas
- Aguarde a IDE resolver as dependências do projeto. Isso pode levar alguns momentos, dependendo da sua conexão com a internet.

5 - Configure as propriedades da aplicação (se necessário)
- Verifique e atualize, se necessário, as propriedades do arquivo application.properties ou application.yml localizado na pasta de recursos do projeto. Essas propriedades podem incluir configurações de banco de dados, configurações do servidor web, entre outras.

6 - Execute a aplicação Spring Boot
- Execute o arquivo principal da aplicação Spring Boot, geralmente nomeado como Application.java ou similar, clicando com o botão direito do mouse e selecionando a opção "Run" ou "Run as" na sua IDE.
- Alternativamente, você pode usar a linha de comando e navegar até o diretório do projeto. Em seguida, execute o comando mvn spring-boot:run (para projetos Maven) ou gradle bootRun (para projetos Gradle) no terminal.

7 - Verifique se a aplicação está sendo executada corretamente
- Após iniciar a aplicação, verifique o console da sua IDE ou o terminal para verificar se a aplicação Spring Boot iniciou sem erros.
- Acesse a API por meio da URL fornecida na configuração do servidor web. Geralmente, é http://localhost:8080 por padrão.
	

 # Como usar a Api

1 - Criar usuário:

- Faça um cadastro utilizando a rota '/userdomains/saveuserdomain'.
- Envie os dados necessários para criar um novo usuário, como nome de usuário, senha, e outras informações solicitadas.

2 - Fazer o login:

- Para obter um token de autenticação, faça o login na rota '/login'.
- Envie o nome de usuário (username) e senha para autenticar e receber o token JWT.

3 - Listar os produtos:

- Utilize o endpoint '/products/all' para listar todos os produtos disponíveis.
- Faça uma requisição para esse endpoint e obtenha a lista de produtos.

4 - Criar um pedido:

- Utilize o endpoint '/orders/{productId}/{quantityOfProduct}' para criar um novo pedido.
- Substitua "{productId}" pelo ID do produto desejado e "{quantityOfProduct}" pela quantidade que deseja pedir.
- Faça uma requisição para esse endpoint, passando os parâmetros corretos, para criar um novo pedido.

5 - Fazer o pagamento do pedido:

- Realize o pagamento do pedido utilizando o endpoint '/payment/{idOrder}'.
- Substitua "{idOrder}" pelo ID do pedido que deseja pagar.
- Faça uma requisição para esse endpoint para efetuar o pagamento do pedido.

Lembre-se de incluir o token JWT nos cabeçalhos das requisições subsequentes após fazer o login. O token de autenticação será retornado após o login e deve ser enviado no cabeçalho Authorization de todas as solicitações subsequentes.

