package com.edurbs.openfood.api.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.FotoProdutoAssembler;
import com.edurbs.openfood.api.model.FotoProdutoApiModel;
import com.edurbs.openfood.api.model.input.FotoProdutoInput;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.FotoProdutoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.ProdutoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.StorageException;
import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.repository.ProdutoRepository;
import com.edurbs.openfood.domain.service.CadastroProdutoService;
import com.edurbs.openfood.domain.service.CatalogoFotoProdutoService;
import com.edurbs.openfood.domain.service.FotoStorageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    
    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoApiModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        
        Produto produto = cadastroProdutoService.buscar(produtoId);
       
        FotoProduto fotoProduto = new FotoProduto();
            
        fotoProduto.setId(produtoId);
        fotoProduto.setProduto(produto);
        fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        fotoProduto.setRestauranteId(restauranteId);
        try {
            fotoProduto.setInputStream(fotoProdutoInput.getArquivo().getInputStream());
        } catch (IOException e) {
            throw new StorageException("Erro ao obter dados da foto", e);            
        }

        FotoProduto fotoProdutoSalva = catalogoFotoProdutoService.salvar(fotoProduto);

        return fotoProdutoAssembler.toApiModel(fotoProdutoSalva);        
       
       
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoApiModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
        return fotoProdutoAssembler.toApiModel(fotoProduto);
    }

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();            
        }
    }
    
    
}
