package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.PedidoResumoApiModel;
import com.edurbs.openfood.domain.model.Pedido;

@Component
public class PedidoResumoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoApiModel toApiModel(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoApiModel.class);
    }

    public List<PedidoResumoApiModel> toCollectionApiModel(Collection<Pedido> pedidos){
        return pedidos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    // public Pedido toDomainModel(PedidoInput pedidoInput){
    //     return modelMapper.map(pedidoInput, Pedido.class);
    // }

    // public void copyToDomainModel(PedidoInput sourcePedidoInput, Pedido destinationPedido){       
        
    //     modelMapper.map(sourcePedidoInput, destinationPedido);
        
    // }
}
