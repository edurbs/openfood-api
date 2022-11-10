insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

INSERT INTO openfood.restaurante
(ativo,  data_cadastro, nome, taxa_frete, cozinha_id)
VALUES( 1, '2022-11-10 01:11:09.025722', 'Da mamãe', 5.00, 1);

INSERT INTO openfood.restaurante
(ativo, data_cadastro, nome, taxa_frete, cozinha_id)
VALUES( 1, '2022-11-10 01:11:09.025722', 'Da titia', 4.00, 1);

INSERT INTO openfood.restaurante
(ativo, data_cadastro, nome, taxa_frete, cozinha_id)
VALUES(1, '2022-11-10 01:11:09.025722', 'Da prima', 6.00, 2);