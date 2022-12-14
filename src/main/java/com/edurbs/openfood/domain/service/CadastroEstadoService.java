package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    private static final String ESTADO_EM_USO = "Estado código %d em uso";
    @Autowired
    EstadoRepository estadoRepository;

    public Estado buscar(Long id) {
        return estadoRepository.findById(id)
            .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    public void remover(Long id) {
        try {
            estadoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        }
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado) ;  
    }

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

}
