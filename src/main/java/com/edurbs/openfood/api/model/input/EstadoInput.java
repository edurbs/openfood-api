package com.edurbs.openfood.api.model.input;

import jakarta.validation.constraints.NotBlank;

public class EstadoInput {
    
    @NotBlank
    private String nome;
}
