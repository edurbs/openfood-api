package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.ProdutoNaoEncontradoException;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto buscar(Long produtoId){
        return produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
        
    }

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void remover(Long produtoId){
        try {
            produtoRepository.deleteById(produtoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(produtoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Produto está em uso e não pode ser excluído.");
        }

    }

    public List<Produto> listarDoRestaurante(Long restauranteId) {
        cadastroRestauranteService.buscar(restauranteId);
        return produtoRepository.findByRestauranteId(restauranteId);        
    }

    public Produto buscarDoRestaurante(Long restauranteId, Long produtoId) {
        cadastroRestauranteService.buscar(restauranteId);        
        return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId)
                .orElseThrow(()-> new ProdutoNaoEncontradoException(String.format("Produto código %d não existe no restaurante código %d.", produtoId, restauranteId)));
    }

    public Produto salvarNoRestaurante(Produto produto, Long restauranteId) {
        var restaurante =  cadastroRestauranteService.buscar(restauranteId);        
        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);        
    }
    
}
