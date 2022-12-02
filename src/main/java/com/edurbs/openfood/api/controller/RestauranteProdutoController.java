package com.edurbs.openfood.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.ProdutoModelAssembler;
import com.edurbs.openfood.api.model.ProdutoApiModel;
import com.edurbs.openfood.api.model.input.ProdutoInput;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.service.CadastroProdutoService;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoApiModel> listarProdutosDoRestaurante(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos){
        List<Produto> produtos =null;
        if(incluirInativos){
            produtos = cadastroProdutoService.listarDoRestaurante(restauranteId);
        }else{
            produtos = cadastroProdutoService.listarAtivosDoRestaurante(restauranteId);
        }
        return produtoModelAssembler.toCollectionApiModel(produtos);

    }

    @GetMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoApiModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        
        Produto produto = cadastroProdutoService.buscarDoRestaurante(restauranteId, produtoId);
        return produtoModelAssembler.toApiModel(produto);
        
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoApiModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {        
        Produto produto = produtoModelAssembler.toDomainModel(produtoInput);
        Produto produtoSalvo = cadastroProdutoService.salvarNoRestaurante(produto, restauranteId);
        return produtoModelAssembler.toApiModel(produtoSalvo);
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoApiModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput){
        Produto produto = cadastroProdutoService.buscarDoRestaurante(restauranteId, produtoId);
        produtoModelAssembler.copyToDomainModel(produtoInput, produto);
        Produto produtoSalvo = cadastroProdutoService.salvarNoRestaurante(produto, restauranteId);

        return produtoModelAssembler.toApiModel(produtoSalvo);
    }
    
}
