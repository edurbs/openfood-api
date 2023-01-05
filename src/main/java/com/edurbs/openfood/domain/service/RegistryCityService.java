package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.CidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.model.City;
import com.edurbs.openfood.domain.repository.CityRepository;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Service
public class RegistryCityService {


    private static final String ESTADO_EM_USO = "Estado código %d em uso";

    @Autowired
    CityRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    CadastroEstadoService cadastroEstadoService;


    public City salvar(City cidade) {
        var estadoId = cidade.getEstado().getId();
        var estado = cadastroEstadoService.buscar(estadoId);
        cidade.setEstado(estado);        
        return cidadeRepository.save(cidade);
                  

    }

    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        }


    }

    public List<City> listar() {
        return cidadeRepository.findAll();
    }

    public City find(Long id) {
        return cidadeRepository.findById(id)
            .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

}
