package com.edurbs.openfood.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
    
}