# Gerenciador de pedidos

A api gerenciadoras de pedidos tem por objetivo disponibizar end points para fazer o controler dos pedidos,sendo assim ao receber os produtos e a quantidade deles o processamento consiste em montar um ordem de pedido com o preco de cada produto, o total onde ira somar todos os produtos, e a situacao do pagamento que tem um end point exclusivo para atualizar o pedido a cada etapa. O sistema foi construido em java junto com seu framework spring Boot e seguindo os co
  
 funcionamento da api detalhadamente
 
 * autenticacao
 * controllers 
 
 
  
  
# tecnologias utilizadas
  spring boot<p>
  maven<p>
  jpa/hibernate<p>
  postgreSQL
  
## modelo conceitual

<img src="https://github.com/guilhermewt/assets/blob/main/webservice%20pedidos.PNG">
    
    Webservice gerenciador de pedidos, um projeto que contém as funcionalidades de fazer pedidos, escolher produtos. o sistema pega estes pedidos e retornar as características como preço,preço total dependendo da quantidade de produtos feitos. Faz a verificação do pagamento do pedido e em que etapa está (pago,aguardando pagamento...). O sistema foi construído com seguindo os conceitos de Rest e Json para transferência de dados
