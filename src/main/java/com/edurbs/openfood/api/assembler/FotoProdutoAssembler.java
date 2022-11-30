package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.FotoProdutoApiModel;
import com.edurbs.openfood.domain.model.FotoProduto;

@Component
public class FotoProdutoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoApiModel toApiModel(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoApiModel.class);
    }

    public List<FotoProdutoApiModel> toCollectionApiModel(Collection<FotoProduto> fotoProdutos){
        return fotoProdutos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    // public FotoProduto toDomainModel(FotoProdutoInput fotoProdutoInput){
    //     return modelMapper.map(fotoProdutoInput, FotoProduto.class);
    // }

    // public void copyToDomainModel(FotoProdutoInput sourceFotoProdutoInput, FotoProduto destinationFotoProduto){       
        
    //     modelMapper.map(sourceFotoProdutoInput, destinationFotoProduto);
        
    // }
}
