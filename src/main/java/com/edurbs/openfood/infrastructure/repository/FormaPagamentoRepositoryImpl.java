package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FormaPagamento> todas() {
        return entityManager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    @Transactional
    public FormaPagamento adicionar(FormaPagamento formaPagamento) {        
        return entityManager.merge(formaPagamento);
    }

    @Override
    public FormaPagamento porId(Long id) {        
        return entityManager.find(FormaPagamento.class, id);
    }

    @Override
    @Transactional
    public void remover(FormaPagamento formaPagamento) {
        entityManager.remove(porId(formaPagamento.getId()));
        
    }
    
}
