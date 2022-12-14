CREATE TABLE openfood_dev.foto_produto (
	produto_id BIGINT NOT NULL,
	nome_arquivo varchar(150) NOT NULL,
	descricao varchar(150) NULL,
	content_type varchar(80) NOT NULL,
	tamanho BIGINT NOT NULL,
	CONSTRAINT foto_produto_pk PRIMARY KEY (produto_id),
	CONSTRAINT foto_produto_FK FOREIGN KEY (produto_id) REFERENCES openfood_dev.produto(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
