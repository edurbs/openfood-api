package com.edurbs.openfood.api.model.mixin;

import com.edurbs.openfood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {
    
    @JsonIgnoreProperties(value="nome", allowGetters = true)
    private Estado estado;
}
