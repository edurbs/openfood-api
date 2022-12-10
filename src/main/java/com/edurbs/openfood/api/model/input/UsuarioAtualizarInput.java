package com.edurbs.openfood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizarInput {
    
    
    // @Schema(description = "Nome do usuário", requiredMode = RequiredMode.REQUIRED, required = true)
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
