package com.edurbs.openfood.infrastructure.repository.specifications;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.edurbs.openfood.domain.model.Restaurante;

public class RestauranteSpecs {
    
    public static Specification<Restaurante> fromFreteGratis() {
        return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);        
    }

    public static Specification<Restaurante> ativos(){
        return (root, query, builder) -> builder.equal(root.get("ativo"), true);        
    }
}
