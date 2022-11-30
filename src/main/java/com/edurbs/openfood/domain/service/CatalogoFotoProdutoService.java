package com.edurbs.openfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Long restauranteId = fotoProduto.getRestauranteId();
        Long produtoId = fotoProduto.getProduto().getId();
        
        cadastroRestauranteService.buscar(restauranteId);

        produtoRepository
                .findFotoById(restauranteId, produtoId)
                .ifPresent(this::remover);

        return produtoRepository.save(fotoProduto);
    }

    public void remover(FotoProduto fotoProduto){
        produtoRepository.delete(fotoProduto);
    }
}
