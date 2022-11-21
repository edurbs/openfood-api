package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.EstadoApiModel;
import com.edurbs.openfood.api.model.input.EstadoInput;
import com.edurbs.openfood.domain.model.Estado;

@Component
public class EstadoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public EstadoApiModel toApiModel(Estado estado){
        return modelMapper.map(estado, EstadoApiModel.class);
    }

    public List<EstadoApiModel> toCollectionApiModel(List<Estado> estados){
        return estados.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Estado toDomainModel(EstadoInput estadoInput){
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainModel(EstadoInput estadoInput, Estado estado){
        modelMapper.map(estadoInput, estado);
        
    }
}
