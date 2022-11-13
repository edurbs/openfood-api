package com.edurbs.openfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Estado buscar(Long id) {
        return estadoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Estado código %d não existe", id)));
    }

    public void remover(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado código %d em uso", id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Estado %d não encontrado", id));
        }
    }

    public Estado salvar(Estado estado) {
        Long estadoId = estado.getId();
        if(Optional.ofNullable(estadoId).isPresent()){
            Optional.ofNullable(buscar(estadoId)).ifPresentOrElse(
                estadoRepository::save, 
                () -> {throw new EntidadeNaoEncontradaException(String.format("Estado %d não existe", estadoId));});
        }

        return estadoRepository.save(estado) ;   
        
        
    }

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

}
