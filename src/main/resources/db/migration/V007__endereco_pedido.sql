ALTER TABLE pedido CHANGE cidade_id endereco_cidade_id bigint NOT NULL;
ALTER TABLE item_pedido CHANGE precoTotal preco_total decimal(10,0) NOT NULL;
