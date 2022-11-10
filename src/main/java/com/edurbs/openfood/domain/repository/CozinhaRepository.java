package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Cozinha;

public interface CozinhaRepository {
    List<Cozinha> todas();    
    Cozinha adicionar(Cozinha cozinha);
    Cozinha porId(Long id);
    void remover(Cozinha cozinha);


}
