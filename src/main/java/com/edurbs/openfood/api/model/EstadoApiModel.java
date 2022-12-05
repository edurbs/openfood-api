package com.edurbs.openfood.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoApiModel  implements Serializable{
    private static final long serialVersionUID = 990634579295804658L;
    private Long id;
    private String nome;
}
