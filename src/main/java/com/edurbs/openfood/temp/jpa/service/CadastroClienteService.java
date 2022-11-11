package com.edurbs.openfood.temp.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.repository.CozinhaRepository;

@Service
public class CadastroClienteService {

    @Autowired
    CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public void remover(Long id){
        
        try {
            cozinhaRepository.remover(id);    
        } catch ( DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format("Cozinha código %d não pode ser removida, pois está em uso", id)
            );
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cozinha com código %d", id)
            );
        }
    }
}
