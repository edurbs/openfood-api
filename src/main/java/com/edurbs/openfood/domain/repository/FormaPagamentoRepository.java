package com.edurbs.openfood.domain.repository;

import java.util.List;

import com.edurbs.openfood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
    List<FormaPagamento> todas();    
    FormaPagamento adicionar(FormaPagamento formaPagamento);
    FormaPagamento porId(Long id);
    void remover(FormaPagamento formaPagamento);
}
