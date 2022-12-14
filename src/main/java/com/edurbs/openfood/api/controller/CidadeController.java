package com.edurbs.openfood.api.controller;

import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;




@RestController
@RequestMapping(value="/cidades", produces = "application/json")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cadastroCidadeService.listar();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cidade buscar(@PathVariable Long id) {        
        return cadastroCidadeService.buscar(id);        
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody  Cidade cidade) {
        try {
            return cadastroCidadeService.salvar(cidade);            
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
       
        Cidade cidadeAtual = cadastroCidadeService.buscar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cadastroCidadeService.salvar(cidadeAtual);            
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        
            cadastroCidadeService.remover(id);
    
    }
    
    
    
}
