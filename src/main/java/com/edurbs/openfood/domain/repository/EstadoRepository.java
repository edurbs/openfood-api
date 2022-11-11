package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Estado;

public interface EstadoRepository {
    List<Estado> listar();
    Estado salvar(Estado estado);
    Estado buscar(Long id);
    void remover(Estado estado);
}
