package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.CidadeApiModel;
import com.edurbs.openfood.api.model.input.CidadeInput;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Estado;

@Component
public class CidadeModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CidadeApiModel toApiModel(Cidade cidade){
        return modelMapper.map(cidade, CidadeApiModel.class);
    }

    public List<CidadeApiModel> toCollectionApiModel(List<Cidade> cidades){
        return cidades.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Cidade toDomainModel(CidadeInput cidadeInput){
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainModel(CidadeInput cidadeInput, Cidade cidade){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of 
        // Estado was altered from 1 to 2
        cidade.setEstado(new Estado());
        
        modelMapper.map(cidadeInput, cidade);
        
    }
}
