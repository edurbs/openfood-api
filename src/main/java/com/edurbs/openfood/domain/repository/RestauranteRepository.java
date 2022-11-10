package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Restaurante;

public interface RestauranteRepository {
    List<Restaurante> todos();
    Restaurante adicionar(Restaurante restaurante);
    Restaurante porId(Long id);
    void remover(Restaurante restaurante);
    
}
