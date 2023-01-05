package com.edurbs.openfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
