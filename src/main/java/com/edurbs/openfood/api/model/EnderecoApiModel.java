package com.edurbs.openfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoApiModel {
    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumoApiModel cidade;
}
