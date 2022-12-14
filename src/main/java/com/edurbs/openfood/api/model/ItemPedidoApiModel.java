package com.edurbs.openfood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoApiModel {
      
    private Long produtoId;

    private String produtoNome;

    private Integer quantidade;
    
    private BigDecimal precoUnitario;
    
    private BigDecimal precoTotal;

    private String observacao;
    

}
