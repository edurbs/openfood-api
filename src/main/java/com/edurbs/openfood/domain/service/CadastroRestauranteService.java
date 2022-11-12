package com.edurbs.openfood.domain.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.CozinhaRepository;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        

        if(Optional.ofNullable(cozinhaRepository.buscar(cozinhaId)).isEmpty()){
            throw new EntidadeNaoEncontradaException(
                    String.format("Cozinha código %d não existe", cozinhaId));
        }

        Optional<Long> restauranteId = Optional.ofNullable(restaurante.getId());
        if(restauranteId.isEmpty()){
            return restauranteRepository.salvar(restaurante);
        }else{
            return Optional.ofNullable(restauranteRepository.buscar(restauranteId.get())) 
                    .map(restauranteAtual -> {
                        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
                        return restauranteRepository.salvar(restaurante);                         
                    })              
                    .orElseThrow( () -> new EntidadeNaoEncontradaException(String.format("Restaurante código %d não existe", restauranteId)));
        }
                        

    }

    public void remover(Long id) {
        try {
            restauranteRepository.remover(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Restaurante código %d não pode ser removido, pois está em uso", id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Restaurante código %d não existe", id));

        }
    }
}
