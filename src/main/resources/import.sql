insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

INSERT INTO openfood.restaurante (ativo,  data_cadastro, nome, taxa_frete, cozinha_id) VALUES( 1, '2022-11-10 01:11:09.025722', 'Da mamãe', 5.00, 1);

INSERT INTO openfood.restaurante (ativo, data_cadastro, nome, taxa_frete, cozinha_id) VALUES( 1, '2022-11-10 01:11:09.025722', 'Da titia', 4.00, 1);

INSERT INTO openfood.restaurante (ativo, data_cadastro, nome, taxa_frete, cozinha_id) VALUES(1, '2022-11-10 01:11:09.025722', 'Da prima', 6.00, 2);



INSERT INTO openfood.permissao (descricao, nome) VALUES('Administradores', 'Admin');
INSERT INTO openfood.permissao (descricao, nome) VALUES('Gerentes', 'Gerente');
INSERT INTO openfood.permissao (descricao, nome) VALUES('Vendedores', 'Vendedor');

INSERT INTO openfood.forma_pagamento (descricao) VALUES('Dinheiro');
INSERT INTO openfood.forma_pagamento (descricao) VALUES('Pix');
INSERT INTO openfood.forma_pagamento (descricao) VALUES('Cartão crédito');
INSERT INTO openfood.forma_pagamento (descricao) VALUES('Cartão débito');

INSERT INTO openfood.estado (id, nome) VALUES(1, 'Minas Gerais');
INSERT INTO openfood.estado (id, nome) VALUES(2, 'São Paulo');
INSERT INTO openfood.estado (id, nome) VALUES(3, 'Rio de Janeiro');

INSERT INTO openfood.cidade (nome, estado_id) VALUES('Varginha', 1);
INSERT INTO openfood.cidade (nome, estado_id) VALUES('Passos', 1);
INSERT INTO openfood.cidade (nome, estado_id) VALUES('Manhumirom', 1);
INSERT INTO openfood.cidade (nome, estado_id) VALUES('Franca', 2);
INSERT INTO openfood.cidade (nome, estado_id) VALUES('Niterói', 3);
