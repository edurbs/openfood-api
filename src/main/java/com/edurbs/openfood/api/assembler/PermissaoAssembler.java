package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.PermissaoApiModel;
import com.edurbs.openfood.api.model.input.PermissaoInput;
import com.edurbs.openfood.domain.model.Permissao;

@Component
public class PermissaoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoApiModel toApiModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoApiModel.class);
    }

    public List<PermissaoApiModel> toCollecApiModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList()
            );
    }

    public Permissao toDomainModel (PermissaoInput permissaoInput){
        return modelMapper.map(permissaoInput, Permissao.class);
    }

    public void copyToDomainModel(PermissaoInput permissaoInput, Permissao permissao) {
        modelMapper.map(permissaoInput, permissao);
    }
    
}
