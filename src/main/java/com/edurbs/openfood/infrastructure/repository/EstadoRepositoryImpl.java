package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Estado> todos() {
        return entityManager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    @Transactional
    public Estado adicionar(Estado estado) {        
        return entityManager.merge(estado);
    }

    @Override
    public Estado porId(Long id) {
        
        return entityManager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public void remover(Estado estado) {
        entityManager.remove(porId(estado.getId()));
        
    }
    
}
