package com.edurbs.openfood.api.model.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoInput {

    @NotBlank
    private String nome;
   
    @NotBlank
    private String descricao;
   
    @PositiveOrZero
    private BigDecimal preco;    
   
    @NonNull
    private Boolean ativo;
}
