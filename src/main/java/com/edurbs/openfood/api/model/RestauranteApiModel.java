package com.edurbs.openfood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteApiModel {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaApiModel cozinha;
    private Boolean ativo;
    private Boolean aberto;

    private EnderecoApiModel endereco;
    
}