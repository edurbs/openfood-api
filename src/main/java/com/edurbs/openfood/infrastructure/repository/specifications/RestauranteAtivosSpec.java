package com.edurbs.openfood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.edurbs.openfood.domain.model.Restaurante;

public class RestauranteAtivosSpec implements Specification<Restaurante> {

    @Override
    @Nullable
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {        
        return criteriaBuilder.equal(root.get("ativo"), true);
    }
    
}
