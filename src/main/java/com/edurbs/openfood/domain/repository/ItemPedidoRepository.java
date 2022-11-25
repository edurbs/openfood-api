package com.edurbs.openfood.domain.repository;

import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends CustomJpaRepository<ItemPedido, Long> {
    
}
