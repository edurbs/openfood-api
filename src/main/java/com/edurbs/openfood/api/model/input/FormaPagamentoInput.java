package com.edurbs.openfood.api.model.input;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FormaPagamentoInput {

    @NotBlank
    private String descricao;
}
