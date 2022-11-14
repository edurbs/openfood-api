

-- pedido definition

CREATE TABLE pedido (
	id BIGINT auto_increment NOT NULL,
	subtotal DECIMAL NOT NULL,
	taxa_frete DECIMAL NOT NULL,
	valor_total DECIMAL NOT NULL,
	data_criacao DATETIME NOT NULL,
	data_confirmacao DATETIME NULL,
	data_entrega DATETIME NULL,
	endereco_cep varchar(100) NOT NULL,
	endereco_logradouro varchar(100) NOT NULL,
	endereco_numero varchar(100) NOT NULL,
	endereco_complemento varchar(100) NULL,
	endereco_bairro varchar(100) NOT NULL,
	cidade_id BIGINT NOT NULL,
	cliente_id BIGINT NOT NULL,
	restaurante_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL,
	status varchar(20) NOT NULL,
	CONSTRAINT pedido_pk PRIMARY KEY (id),
	CONSTRAINT pedido_FK FOREIGN KEY (cidade_id) REFERENCES cidade(id),
	CONSTRAINT pedido_FK_1 FOREIGN KEY (cliente_id) REFERENCES usuario(id),
	CONSTRAINT pedido_FK_2 FOREIGN KEY (restaurante_id) REFERENCES restaurante(id),
	CONSTRAINT pedido_FK_3 FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;


-- item_pedido definition

CREATE TABLE item_pedido (
	id BIGINT auto_increment NOT NULL,
	quantidade INT NOT NULL,
	preco_unitario DECIMAL NOT NULL,
	precoTotal DECIMAL NOT NULL,
	observacao varchar(255) NULL,
	produto_id BIGINT NOT NULL,
	pedido_id BIGINT NOT NULL,
	CONSTRAINT item_pedido_pk PRIMARY KEY (id),
	CONSTRAINT item_pedido_FK FOREIGN KEY (produto_id) REFERENCES produto(id),
	CONSTRAINT item_pedido_FK_1 FOREIGN KEY (pedido_id) REFERENCES pedido(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;