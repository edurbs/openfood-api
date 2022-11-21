package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.CozinhaApiModel;
import com.edurbs.openfood.api.model.input.CozinhaInput;
import com.edurbs.openfood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaApiModel toApiModel(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaApiModel.class);
    }

    public List<CozinhaApiModel> toCollectionApiModel(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Cozinha toDomainModel(CozinhaInput cozinhaInput){
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainModel(CozinhaInput cozinhaInput, Cozinha cozinha){
        modelMapper.map(cozinhaInput, cozinha);
        
    }
}
