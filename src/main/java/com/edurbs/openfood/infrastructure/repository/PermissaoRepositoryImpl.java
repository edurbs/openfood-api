package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Permissao;
import com.edurbs.openfood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Permissao> todas() {
        return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    @Transactional
    public Permissao adicionar(Permissao permissao) {
        return entityManager.find(Permissao.class, permissao);
    }

    @Override
    public Permissao porId(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        entityManager.remove(porId(permissao.getId()));        
    }
    
}
