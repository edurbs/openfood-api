package com.edurbs.openfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

    List<Produto> findByRestauranteId(Long restauranteId);

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

    List<Produto> findByRestauranteIdAndAtivoIsTrue(Long restauranteId);
    
}
