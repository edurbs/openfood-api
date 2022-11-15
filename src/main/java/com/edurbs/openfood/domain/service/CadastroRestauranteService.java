package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.RestauranteNaoEncontradoException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    private static final String RESTAURANTE_EM_USO = "Restaurante código %d não pode ser removido, pois está em uso";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscar(cozinhaId);
        restaurante.setCozinha(cozinha);

        Cidade cidade = cadastroCidadeService.buscar(restaurante.getEndereco().getCidade().getId());
        restaurante.getEndereco().setCidade(cidade);
        
        return restauranteRepository.save(restaurante);
    }

    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(RESTAURANTE_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);

        }
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
