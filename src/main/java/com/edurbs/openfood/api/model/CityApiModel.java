package com.edurbs.openfood.api.model;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityApiModel extends RepresentationModel<CityApiModel> implements Serializable {
    
    private static final long serialVersionUID = -924440620432145890L;

    private Long id;
    private String name;
    private EstadoApiModel estado;
}
