package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Cidade;

public interface CidadeRepository {
    List<Cidade> listar();
    Cidade salvar(Cidade cidade);
    Cidade buscar(Long id);
    void remover(Cidade cidade);
}
