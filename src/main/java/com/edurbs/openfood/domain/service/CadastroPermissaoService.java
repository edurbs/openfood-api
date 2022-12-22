package com.edurbs.openfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntityNotFoundException;
import com.edurbs.openfood.domain.exception.PermissaoNaoEncontradaException;
import com.edurbs.openfood.domain.model.Permissao;
import com.edurbs.openfood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
    
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscar(Long permissaoId) {
        
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
        
    }
}
