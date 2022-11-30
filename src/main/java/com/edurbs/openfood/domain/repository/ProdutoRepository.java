package com.edurbs.openfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    List<Produto> findByRestauranteId(Long restauranteId);

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

    List<Produto> findByRestauranteIdAndAtivoIsTrue(Long restauranteId);

    @Query("select f from FotoProduto f join f.produto p where f.produto.id = :produtoId and p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);
    
}
