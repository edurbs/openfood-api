package com.edurbs.openfood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.FormaPagamentoAssembler;
import com.edurbs.openfood.api.model.FormaPagamentoApiModel;
import com.edurbs.openfood.api.model.input.FormaPagamentoInput;
import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.service.CadastroFormaPagamentoService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/formaspagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoApiModel>> listar() {
        List<FormaPagamento> lista = cadastroFormaPagamentoService.listar();
        List<FormaPagamentoApiModel> listaApiModel = formaPagamentoAssembler.toCollectionApiModel(lista);
        CacheControl maxAge = CacheControl.maxAge(10, TimeUnit.SECONDS);
        return ResponseEntity.ok()
                .cacheControl(maxAge)
                    .body(listaApiModel);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoApiModel buscar(@PathVariable Long id) {
        return formaPagamentoAssembler.toApiModel(cadastroFormaPagamentoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoApiModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        var formaPagamento = formaPagamentoAssembler.toDomainModel(formaPagamentoInput);
        var formaPagamentoSalvo = cadastroFormaPagamentoService.salvar(formaPagamento);
        return formaPagamentoAssembler.toApiModel(formaPagamentoSalvo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoApiModel atualizar (@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        var formaPagamentoAtual = cadastroFormaPagamentoService.buscar(id);

        formaPagamentoAssembler.copyToDomainModel(formaPagamentoInput, formaPagamentoAtual);
        var formaPagamentoSalvo = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);

        return formaPagamentoAssembler.toApiModel(formaPagamentoSalvo);        
        
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.remover(id);
    }
    
    
    
    
}
