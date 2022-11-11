package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Cozinha;

public interface CozinhaRepository {
    List<Cozinha> listar();    
    Cozinha salvar(Cozinha cozinha);
    Cozinha buscar(Long id);
    void remover(Long id);


}
