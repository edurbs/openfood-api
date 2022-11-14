insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');



INSERT INTO forma_pagamento (descricao) VALUES('Dinheiro');
INSERT INTO forma_pagamento (descricao) VALUES('Pix');
INSERT INTO forma_pagamento (descricao) VALUES('Cartão crédito');
INSERT INTO forma_pagamento (descricao) VALUES('Cartão débito');

INSERT INTO estado (id, nome) VALUES(1, 'Minas Gerais');
INSERT INTO estado (id, nome) VALUES(2, 'São Paulo');
INSERT INTO estado (id, nome) VALUES(3, 'Rio de Janeiro');
INSERT INTO estado (id, nome) VALUES(4, 'Mato Grosso do Sul');
INSERT INTO estado (id, nome) VALUES(5, 'Rio Grande do Sul');
INSERT INTO estado (id, nome) VALUES(6, 'Mato Grosso do Norte');
INSERT INTO estado (id, nome) VALUES(7, 'Amazonas');

INSERT INTO cidade (nome, estado_id) VALUES('Varginha', 1);
INSERT INTO cidade (nome, estado_id) VALUES('Passos', 1);
INSERT INTO cidade (nome, estado_id) VALUES('Manhumirom', 1);
INSERT INTO cidade (nome, estado_id) VALUES('Franca', 2);
INSERT INTO cidade (nome, estado_id) VALUES('Niterói', 3);

INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da mamãe', 5.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo,nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da titia', 4.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES(1, 'Da prima', 6.00, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da sogra', 5.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo,nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da esposa', 4.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES(1, 'Da vizinha', 6.00, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da irmã', 5.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo,nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES( 1, 'Da filha', 4.00, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante (ativo, nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro) VALUES(1, 'Da neta', 6.00, 2, utc_timestamp, utc_timestamp);

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into permissao (nome, descricao) values ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (nome, descricao) values ('EDITAR_COZINHAS', 'Permite editar cozinhas');