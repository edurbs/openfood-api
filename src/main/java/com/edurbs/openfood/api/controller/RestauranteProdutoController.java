package com.edurbs.openfood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.FotoProdutoAssembler;
import com.edurbs.openfood.api.model.FotoProdutoApiModel;
import com.edurbs.openfood.api.model.input.FotoProdutoInput;
import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.service.CadastroProdutoService;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;
import com.edurbs.openfood.domain.service.CatalogoFotoProdutoService;


@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoController {
    
    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

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

        FotoProduto fotoProdutoSalva = catalogoFotoProdutoService.salvar(fotoProduto);

        return fotoProdutoAssembler.toApiModel(fotoProdutoSalva);        
    }
}
