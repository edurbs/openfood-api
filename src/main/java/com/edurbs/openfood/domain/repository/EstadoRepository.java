package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.Estado;

public interface EstadoRepository {
    List<Estado> todos();
    Estado adicionar(Estado estado);
    Estado porId(Long id);
    void remover(Estado estado);
}
