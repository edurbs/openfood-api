package com.edurbs.openfood.temp.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Cozinha;

@Component
public class CadastroCozinha {

    @PersistenceContext
    EntityManager entityManager;

    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = entityManager.createQuery("from Cozinha", Cozinha.class);
        return query.getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    public Cozinha buscar(long id) {
        return entityManager.find(Cozinha.class, id);        
    }

    @Transactional
    public void remover(Cozinha cozinha){
        cozinha = buscar(cozinha.getId());
        entityManager.remove(cozinha);
    }
}
