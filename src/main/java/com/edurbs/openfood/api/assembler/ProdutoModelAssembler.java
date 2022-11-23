package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.ProdutoApiModel;
import com.edurbs.openfood.api.model.input.ProdutoInput;
import com.edurbs.openfood.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoApiModel toApiModel(Produto produto){
        return modelMapper.map(produto, ProdutoApiModel.class);
    }

    public List<ProdutoApiModel> toCollectionApiModel(List<Produto> produtos){
        return produtos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Produto toDomainModel(ProdutoInput produtoInput){
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainModel(ProdutoInput sourceProdutoInput, Produto destinationProduto){       
        
        modelMapper.map(sourceProdutoInput, destinationProduto);
        
    }
}
