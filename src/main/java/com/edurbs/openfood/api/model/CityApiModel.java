package com.edurbs.openfood.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityApiModel implements Serializable {
    

    private static final long serialVersionUID = -8881742535695619217L;

    private Long id;
    private String name;
    private EstadoApiModel estado;
}
