package com.edurbs.openfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.FormaPagamentoAssembler;
import com.edurbs.openfood.api.model.FormaPagamentoApiModel;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;



@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;


    @GetMapping()    
    @ResponseStatus(HttpStatus.OK)
    public List<FormaPagamentoApiModel> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscar(restauranteId);
        var formasPagamento = restaurante.getFormasPagamento();

        return formaPagamentoAssembler.toCollectionApiModel(formasPagamento);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

    



}
