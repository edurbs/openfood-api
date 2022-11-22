set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

INSERT ignore INTO forma_pagamento (id, descricao) VALUES(1, 'Dinheiro');
INSERT ignore INTO forma_pagamento (id, descricao) VALUES(2, 'Pix');
INSERT ignore INTO forma_pagamento (id, descricao) VALUES(3, 'Cartão crédito');
INSERT ignore INTO forma_pagamento (id, descricao) VALUES(4, 'Cartão débito');

INSERT ignore INTO estado (id, nome) VALUES(1, 'Minas Gerais');
INSERT ignore INTO estado (id, nome) VALUES(2, 'São Paulo');
INSERT ignore INTO estado (id, nome) VALUES(3, 'Rio de Janeiro');
INSERT ignore INTO estado (id, nome) VALUES(4, 'Mato Grosso do Sul');
INSERT ignore INTO estado (id, nome) VALUES(5, 'Rio Grande do Sul');
INSERT ignore INTO estado (id, nome) VALUES(6, 'Mato Grosso do Norte');
INSERT ignore INTO estado (id, nome) VALUES(7, 'Amazonas');

INSERT ignore INTO cidade (id, nome, estado_id) VALUES(1, 'Varginha', 1);
INSERT ignore INTO cidade (id, nome, estado_id) VALUES(2, 'Passos', 1);
INSERT ignore INTO cidade (id, nome, estado_id) VALUES(3, 'Manhumirom', 1);
INSERT ignore INTO cidade (id, nome, estado_id) VALUES(4, 'Franca', 2);
INSERT ignore INTO cidade (id, nome, estado_id) VALUES(5, 'Niterói', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true);

insert ignore into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (4, 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (5, 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (6, 'Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (7, 'T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (8, 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (9, 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert ignore into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert ignore into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert ignore into grupo (nome) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');