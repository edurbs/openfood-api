package com.edurbs.openfood.domain.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.FotoProdutoNaoEncontradoException;
import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.repository.ProdutoRepository;
import com.edurbs.openfood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService storageFotoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Long restauranteId = fotoProduto.getRestauranteId();

        Long produtoId = fotoProduto.getProduto().getId();
        cadastroProdutoService.buscarDoRestaurante(restauranteId, produtoId);
        

        String novoNomeArquivo = storageFotoService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        fotoProduto.setNomeArquivo(novoNomeArquivo);
        
        produtoRepository
                .findFotoById(restauranteId, produtoId)
                .ifPresent(this::remover);
        
        var fotoSalva = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        var novaFotoStorage = NovaFoto.builder()
                .nomeArquivo(novoNomeArquivo)
                .inputStream(fotoProduto.getInputStream())
                .contentType(fotoProduto.getContentType())
                .size(fotoProduto.getTamanho())
                .build();

        storageFotoService.armazenar(novaFotoStorage);

        return fotoSalva;
    }

    @Transactional
    public void remover(FotoProduto fotoProduto){
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();
        storageFotoService.excluir(fotoProduto.getNomeArquivo());
    }

    public void remover(Long restauranteId, Long produtoId){
        var fotoProduto = buscar(restauranteId, produtoId);
        remover(fotoProduto);
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
        
    }
}
