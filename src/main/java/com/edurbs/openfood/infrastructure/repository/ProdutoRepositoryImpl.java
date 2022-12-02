package com.edurbs.openfood.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
