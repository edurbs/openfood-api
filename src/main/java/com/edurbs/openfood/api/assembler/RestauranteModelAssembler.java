package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.RestauranteApiModel;
import com.edurbs.openfood.api.model.input.RestauranteInput;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteApiModel toApiModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteApiModel.class);    
    }

    public List<RestauranteApiModel> toCollectionApiModel(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Restaurante toDomainModel(@Valid RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainModel(@Valid RestauranteInput restauranteInput, Restaurante restaurante){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of 
        // ...  was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }
    

}
