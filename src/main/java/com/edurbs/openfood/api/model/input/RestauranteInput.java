package com.edurbs.openfood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.edurbs.openfood.core.validation.Multiplo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @NotNull
    //@PositiveOrZero
    //@TaxaFrete
    @Multiplo(5)    
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;
}
