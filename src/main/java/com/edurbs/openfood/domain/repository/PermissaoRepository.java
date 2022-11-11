package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Permissao;

public interface PermissaoRepository {
    List<Permissao> listar();
    Permissao salvar(Permissao permissao);
    Permissao buscar(Long id);
    void remover(Permissao permissao);
}
