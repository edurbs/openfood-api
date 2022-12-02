package com.edurbs.openfood.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAdicionarInput extends UsuarioAtualizarInput{
    @NotBlank
    private String senha;
}
