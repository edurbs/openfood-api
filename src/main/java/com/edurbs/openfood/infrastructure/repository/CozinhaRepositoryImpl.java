package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Cozinha> todas() {
        TypedQuery<Cozinha> query = entityManager.createQuery("from Cozinha", Cozinha.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Cozinha adicionar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    @Override
    public Cozinha porId(Long id) {
        return entityManager.find(Cozinha.class, id);  
    }

    @Override
    @Transactional
    public void remover(Cozinha cozinha) {
        Cozinha cozinhaDoBanco = porId(cozinha.getId());
        entityManager.remove(cozinhaDoBanco);
        
    }
    
}
