package com.edurbs.openfood.infrastructure.repository.specifications;

import java.util.ArrayList;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.edurbs.openfood.domain.filter.PedidoFilter;
import com.edurbs.openfood.domain.model.Pedido;

public class PedidoSpecs {
    public static Specification<Pedido> usandoFiltro(PedidoFilter pedidoFilter) {
        return (root, query, builder) -> {
            
            // para não fazer o fetch quando for fazer um select count (para pageable)
            if(Pedido.class.equals(query.getResultType())){
                // para não fazer vários selects duplicados, problema do N+1
                root.fetch("restaurante").fetch("cozinha"); 
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();
            
            if(pedidoFilter.getClienteId() != null){
                predicates.add(builder.equal(root.get("cliente"), pedidoFilter.getClienteId()));
            }

            if(pedidoFilter.getRestauranteId() != null){
                predicates.add(builder.equal(root.get("restaurante"), pedidoFilter.getRestauranteId()));
            }

            if(pedidoFilter.getDataCriacaoInicio()!= null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoInicio()));
            }

            if(pedidoFilter.getDataCriacaoFim()!= null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };        
    }
}
