package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.PedidoNaoEncontradoException;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscar(Long pedidoId) {        
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));        
    }


}
