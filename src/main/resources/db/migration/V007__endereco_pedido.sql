ALTER TABLE pedido CHANGE city_id endereco_city_id bigint NOT NULL;
ALTER TABLE item_pedido CHANGE precoTotal preco_total decimal(10,0) NOT NULL;
