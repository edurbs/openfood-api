package com.edurbs.openfood.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
    
    @NotNull
    private Long produtoId;

    @Positive
    @NotNull
    private Integer quantidade;

    private String observacao;

}
