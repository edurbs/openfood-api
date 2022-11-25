package com.edurbs.openfood.domain.repository;

import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
    
}
