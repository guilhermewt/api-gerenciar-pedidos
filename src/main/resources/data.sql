INSERT INTO tb_role(role_id,role_name)
values(1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO tb_usuario(id,nome,email,telefone,password,username)
values(default,'guilherme','gui@gmail.com','3332112','$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y','guilhermeSilva');

INSERT INTO tb_usuario(id,nome,email,telefone,password,username)
values(default,'welliston','welliston@gmail.com','737423','$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y','wellistonPereira');

INSERT INTO tb_usuario_roles(usuario_id,role_id)
values(1,1),(2,2);




INSERT INTO tb_pedido(id,moment,order_status,usuario_id)
values(default,'2019-06-20T16:40:05Z',2,1);


INSERT INTO tb_pedido(id,moment,order_status,usuario_id)
values(default,'2019-04-03T16:50:04Z',1,2);

INSERT INTO tb_pedido(id,moment,order_status,usuario_id)
values(default,'2022-04-20T16:51:59Z',1,2);



INSERT INTO tb_pagamento(moment,pedido_id)
values('2019-06-20T16:50:05Z',1);




INSERT INTO tb_produto(id,descricao,img_url,nome,preco)
values(default,'livro de terror','','A freira',90.9);

INSERT INTO tb_produto(id,descricao,img_url,nome,preco)
values(default,'computador asus','','asus 3012',100.9);

INSERT INTO tb_produto(id,descricao,img_url,nome,preco)
values(default,'samsung tv 32 polegada','','smart tv',2010.9);

INSERT INTO tb_produto(id,descricao,img_url,nome,preco)
values(default,'geladeira consul','','geladeira',4000.9);

INSERT INTO tb_produto(id,descricao,img_url,nome,preco)
values(default,'drama','','A Menina Que Roubava Livros - Markus suzak',39.0);







INSERT INTO tb_categoria(id,nome)
values(default,'books');

INSERT INTO tb_categoria(id,nome)
values(default,'eletronicos');

INSERT INTO tb_categoria(id,nome)
values(default,'computadores');






INSERT INTO tb_produto_categoria(produto_id,categoria_id)
values(1,1),(2,3),(3,2),(3,3),(4,2),(5,1);



INSERT INTO tb_items_de_pedido(preco,quantidade,produto_id,pedido_id)
values(90.9,2,1,1),(4000.9,3,4,3),(100.9,5,2,2),(2010.9,4,3,1);





