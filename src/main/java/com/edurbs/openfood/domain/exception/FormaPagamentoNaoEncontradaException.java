package com.edurbs.openfood.domain.exception;

import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.repository.FormaPagamentoRepository;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {



    public FormaPagamentoNaoEncontradaException(String msg) {
        super(msg);       
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Forma de pagamento código %d não existe.", id));       
    }

    
}
