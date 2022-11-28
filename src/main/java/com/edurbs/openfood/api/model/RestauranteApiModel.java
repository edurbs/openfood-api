package com.edurbs.openfood.api.model;

import java.math.BigDecimal;

import com.edurbs.openfood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteApiModel {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    private CozinhaApiModel cozinha;
    private Boolean ativo;
    private Boolean aberto;

    private EnderecoApiModel endereco;
    
}