package com.edurbs.openfood.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CidadeApiModel {

    private Long id;
    private String nome;
    private EstadoApiModel estado;
}
