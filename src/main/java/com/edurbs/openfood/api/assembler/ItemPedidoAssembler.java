package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.ItemPedidoApiModel;
import com.edurbs.openfood.domain.model.ItemPedido;

@Component
public class ItemPedidoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoApiModel toApiModel(ItemPedido itemPedido){
        return modelMapper.map(itemPedido, ItemPedidoApiModel.class);
    }

    public List<ItemPedidoApiModel> toCollectionApiModel(Collection<ItemPedido> itemPedidos){
        return itemPedidos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    // public ItemPedido toDomainModel(ItemPedidoInput itemPedidoInput){
    //     return modelMapper.map(itemPedidoInput, ItemPedido.class);
    // }

    // public void copyToDomainModel(ItemPedidoInput sourceItemPedidoInput, ItemPedido destinationItemPedido){       
        
    //     modelMapper.map(sourceItemPedidoInput, destinationItemPedido);
        
    // }
}
