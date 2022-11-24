CREATE TABLE openfood_dev.restaurante_usuario_responsavel (
	usuario_id BIGINT NULL,
	restaurante_id BIGINT NULL,
	CONSTRAINT restaurante_usuario_responsavel_FK FOREIGN KEY (usuario_id) REFERENCES openfood_dev.usuario(id),
	CONSTRAINT restaurante_usuario_responsavel_FK_1 FOREIGN KEY (restaurante_id) REFERENCES openfood_dev.restaurante(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
