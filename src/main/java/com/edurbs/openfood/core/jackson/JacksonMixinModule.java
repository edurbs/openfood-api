package com.edurbs.openfood.core.jackson;

import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.mixin.CidadeMixin;
import com.edurbs.openfood.api.model.mixin.RestauranteMixin;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule{

    private static final long serialVersionUID=1L;

    public JacksonMixinModule(){
        //setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }

}
