package com.edurbs.openfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.CozinhaRepository;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    private static final String RESTAURANTE_EM_USO = "Restaurante código %d não pode ser removido, pois está em uso";
    private static final String COZINHA_NAO_EXISTE = "Cozinha código %d não existe";
    private static final String RESTAURANTE_NAO_EXISTE = "Restaurante código %d não existe";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        

        if(cozinhaRepository.findById(cozinhaId).isEmpty()){
            throw new EntidadeNaoEncontradaException(
                    String.format(COZINHA_NAO_EXISTE, cozinhaId));
        }

        Optional<Long> restauranteId = Optional.ofNullable(restaurante.getId());
        if(restauranteId.isEmpty()){
            var restauranteSalvo = restauranteRepository.save(restaurante);
            return restauranteRepository.findById(restauranteSalvo.getId()).get();
        }else{
            return restauranteRepository.findById(restauranteId.get())
                    .map(restauranteAtual -> {
                        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
                        return restauranteRepository.save(restaurante);                         
                    })              
                    .orElseThrow( () -> new EntidadeNaoEncontradaException(String.format(RESTAURANTE_NAO_EXISTE, restauranteId.get())));
        }
                        

    }

    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(RESTAURANTE_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(RESTAURANTE_NAO_EXISTE, id));

        }
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(RESTAURANTE_NAO_EXISTE, id)));
    }
}
