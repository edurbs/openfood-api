package com.edurbs.openfood.api.assembler;

import javax.validation.Valid;

import com.edurbs.openfood.api.model.input.RestauranteInput;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;

public class RestauranteInputDesassembler {
    public Restaurante toDomainModel(@Valid RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
