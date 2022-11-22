package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.GrupoApiModel;
import com.edurbs.openfood.api.model.input.GrupoInput;
import com.edurbs.openfood.domain.model.Grupo;

@Component
public class GrupoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public GrupoApiModel toApiModel(Grupo grupo){
        return modelMapper.map(grupo, GrupoApiModel.class);
    }

    public List<GrupoApiModel> toCollectionApiModel(List<Grupo> grupos){
        return grupos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Grupo toDomainModel(GrupoInput grupoInput){
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainModel(GrupoInput sourceGrupoInput, Grupo destinationGrupo){       
        
        modelMapper.map(sourceGrupoInput, destinationGrupo);
        
    }
}
