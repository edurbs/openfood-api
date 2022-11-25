package com.edurbs.openfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.PedidoAssembler;
import com.edurbs.openfood.api.model.PedidoApiModel;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.service.CadastroPedidoService;



@RestController
@RequestMapping("/pedidos")
public class PedidoCOntroller {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private PedidoAssembler pedidoAssembler;

    @GetMapping
    public List<PedidoApiModel> listar() {
        List<Pedido> pedidos = cadastroPedidoService.listar();
        return pedidoAssembler.toCollectionApiModel(pedidos);
    }

    @GetMapping(value="/{pedidoId}")
    public PedidoApiModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = cadastroPedidoService.buscar(pedidoId);
        return pedidoAssembler.toApiModel(pedido);
    }
    
    
    
}
