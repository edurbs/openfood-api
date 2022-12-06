package com.edurbs.openfood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Usuário", description = "Representa um usuário")
@Getter
@Setter
public class UsuarioApiModel {

    @Schema(description = "ID do usuário")
    private Long id;

    @Schema(description = "Nome do usuário", example = "Maria da Silva")
    private String nome;
    private String email;
}
