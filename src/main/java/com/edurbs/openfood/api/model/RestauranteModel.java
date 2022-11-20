package com.edurbs.openfood.api.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteModel {
    Long id;
    String nome;
    BigDecimal taxaFrete;
    CozinhaModel cozinha;
    
}