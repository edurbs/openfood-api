package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
    List<FormaPagamento> listar();    
    FormaPagamento salvar(FormaPagamento formaPagamento);
    FormaPagamento buscar(Long id);
    void remover(FormaPagamento formaPagamento);
}
