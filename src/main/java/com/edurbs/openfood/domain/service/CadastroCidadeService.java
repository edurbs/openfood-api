package com.edurbs.openfood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.repository.CidadeRepository;
import com.edurbs.openfood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        var estadoId = Optional.ofNullable(cidade.getEstado().getId());
        if(estadoId.isPresent()){
            if (estadoRepository.findById(estadoId.get()).isEmpty()) {
                throw new EntidadeNaoEncontradaException(
                        String.format("Estado código %d não existe.", estadoId.get()));
            }
        }
        var cidadeId = Optional.ofNullable(cidade.getId());
        if (cidadeId.isPresent()) {
            return cidadeRepository.findById(cidadeId.get())
                    .map(cidadeAtual -> {
                        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
                        return cidadeRepository.save(cidade);
                    }).orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Cidade código %d não encontrada", cidadeId.get())));
        }

        Cidade cidadeSalva = cidadeRepository.save(cidade);
        System.out.println(cidadeSalva.getEstado().getNome());
        return cidadeSalva;

    }

    public void remover(Long id) {
        cidadeRepository.findById(id)
                .ifPresentOrElse(c -> cidadeRepository.delete(c),
                        () -> {
                            throw new EntidadeNaoEncontradaException(
                                    String.format("Cidade código %d não existe", id));
                        });

    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long id) {
        return cidadeRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Cidade código %d não existe", id)));
    }

}
