package com.edurbs.openfood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }

    @Override
    @Transactional
    public void delete(FotoProduto fotoProduto) {
        entityManager.remove(fotoProduto);        
    }
    
}
