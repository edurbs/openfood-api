package com.edurbs.openfood.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CidadeInput {
    
    @NotBlank
    @Valid
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estado;
}
