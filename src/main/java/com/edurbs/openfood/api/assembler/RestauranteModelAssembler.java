package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.edurbs.openfood.api.model.CozinhaModel;
import com.edurbs.openfood.api.model.RestauranteModel;
import com.edurbs.openfood.api.model.input.RestauranteInput;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;

public class RestauranteModelAssembler {
    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);
        return restauranteModel;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    

}
