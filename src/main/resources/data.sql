INSERT INTO tb_role(role_id,role_name)
values(1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO tb_usuario(id,nome,email,telefone,password,username)
values(default,'guilherme','gui@gmail.com','3332112','$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y','guilhermeSilva');

INSERT INTO tb_usuario(id,nome,email,telefone,password,username)
values(default,'welliston','welliston@gmail.com','737423','$2a$10$r4IKP2iTSemgTjqsI67P.uGz38482kzHArEp8QVZ72tAd3Ii8my3y','wellistonPereira');


INSERT INTO tb_usuario_roles(usuario_id,role_id)
values(1,1),(2,2);

