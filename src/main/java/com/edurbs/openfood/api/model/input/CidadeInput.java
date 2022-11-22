package com.edurbs.openfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


public class CidadeInput {
    
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estadoId;
}
