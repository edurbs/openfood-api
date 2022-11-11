package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Cidade;

public interface CidadeRepository {
    List<Cidade> todas();
    Cidade adicionar(Cidade cidade);
    Cidade porId(Long id);
    void remover(Cidade cidade);
}
