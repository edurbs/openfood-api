package com.edurbs.openfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
