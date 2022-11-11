package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Permissao;

public interface PermissaoRepository {
    List<Permissao> todas();
    Permissao adicionar(Permissao permissao);
    Permissao porId(Long id);
    void remover(Permissao permissao);
}
