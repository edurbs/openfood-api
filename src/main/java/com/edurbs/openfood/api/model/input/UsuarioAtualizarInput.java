package com.edurbs.openfood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizarInput {
    
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
