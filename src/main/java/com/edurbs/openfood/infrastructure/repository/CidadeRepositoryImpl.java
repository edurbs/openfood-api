package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Cidade> todas() {
        return entityManager.createQuery("from Cidade", Cidade.class).getResultList();
    }

    @Override
    @Transactional
    public Cidade adicionar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Override
    public Cidade porId(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public void remover(Cidade cidade) {
        entityManager.remove(porId(cidade.getId()));
    }
    
}
