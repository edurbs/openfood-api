package com.edurbs.openfood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAdicionarInput extends UsuarioAtualizarInput{

    // @Schema(description = "Nome do usuário", requiredMode = RequiredMode.REQUIRED, required = true)
    @NotBlank
    private String senha;
}
