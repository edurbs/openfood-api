package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.UsuarioApiModel;
import com.edurbs.openfood.api.model.input.UsuarioAdicionarInput;
import com.edurbs.openfood.api.model.input.UsuarioAtualizarInput;
import com.edurbs.openfood.domain.model.Usuario;

@Component
public class UsuarioAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioApiModel toApiModel(Usuario usuario){
        return modelMapper.map(usuario, UsuarioApiModel.class);
    }

    public List<UsuarioApiModel> toCollectionApiModel(Collection<Usuario> usuarios){
        return usuarios.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public Usuario toDomainModel(UsuarioAdicionarInput usuarioAdicionarInput){
        return modelMapper.map(usuarioAdicionarInput, Usuario.class);
    }

    public void copyToDomainModel(UsuarioAtualizarInput sourceUsuarioAtualizarInput, Usuario destinationUsuario){       
        
        modelMapper.map(sourceUsuarioAtualizarInput, destinationUsuario);
        
    }
}
