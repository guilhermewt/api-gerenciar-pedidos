# API de pedidos

A API Gerenciadora de Pedidos é um projeto desenvolvido em Java utilizando o framework Spring Boot. Seu objetivo é disponibilizar endpoints para o controle de pedidos, permitindo a criação, consulta e atualização de pedidos.

Com a API, é possível realizar a criação de pedidos com informações detalhadas sobre os produtos e suas quantidades. Além disso, a API calcula automaticamente o preço total do pedido, facilitando o processo de controle financeiro.

Através dos endpoints disponibilizados, os usuários podem consultar as informações de um pedido específico, obtendo detalhes sobre os produtos, quantidades, preço total e situação do pagamento. Além disso, é possível atualizar a situação do pagamento de um pedido, mantendo o status atualizado durante todo o processo.

Portanto, o projeto resolve o problema de controle e gerenciamento de pedidos de forma automatizada, proporcionando uma solução eficiente para empresas que lidam com a gestão de pedidos em suas operações diárias.


## Tabela de conteúdos

Aqui você coloca links para acessar mais facilmente cada um dos tópicos do seu README.

Exemplo:

- [Arquitetura](#arquitetura)
- [springboot](#springboot)

# Arquitetura

### UML do backend
<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/uml-api-pedidos.png">

### UML do banco de dados
<img src="https://github.com/guilhermewt/assets/blob/main/Api%20de%20pedidos/uml-BD-api-pedidos.png">

### springboot
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

 ## instruçoẽs de execução 

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

