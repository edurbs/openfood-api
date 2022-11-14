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
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.repository.CidadeRepository;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {


    /**
     *
     */
    private static final String ESTADO_EM_USO = "Estado código %d em uso";
    private static final String ESTADO_NAO_EXISTE = "Estado código %d não existe";
    private static final String CIDADE_NAO_EXISTE = "Cidade código %d não existe";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        var estadoId = Optional.ofNullable(cidade.getEstado().getId());
        if(estadoId.isPresent()){
            if (estadoRepository.findById(estadoId.get()).isEmpty()) {
                throw new EntidadeNaoEncontradaException(
                        String.format(ESTADO_NAO_EXISTE, estadoId.get()));
            }
        }
        var cidadeId = Optional.ofNullable(cidade.getId());
        if (cidadeId.isPresent()) {
            return cidadeRepository.findById(cidadeId.get())
                    .map(cidadeAtual -> {
                        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
                        return cidadeRepository.save(cidade);
                    }).orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format(CIDADE_NAO_EXISTE, cidadeId.get())));
        }
        return cidadeRepository.save(cidade);

    }

    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_EXISTE, id));
        }


    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long id) {
        return cidadeRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(CIDADE_NAO_EXISTE, id)));
    }

}
