package com.edurbs.openfood.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


public class CidadeInput {
    
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estadoId;
}
