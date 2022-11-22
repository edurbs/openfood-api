package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
    
    private static final String FORMAPAGAMENTO_EM_USO = "Forma de pagamento código %d em uso";

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    
    public void remover(Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(FORMAPAGAMENTO_EM_USO, id));            
        } catch (EmptyResultDataAccessException e){
            throw new FormaPagamentoNaoEncontradaException(id);
        }        
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

}
