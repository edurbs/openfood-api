package com.edurbs.openfood.api.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {
    
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;
}
