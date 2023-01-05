package com.edurbs.openfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.edurbs.openfood.api.assembler.CozinhaModelAssembler;
import com.edurbs.openfood.api.model.CozinhaApiModel;
import com.edurbs.openfood.api.model.input.CozinhaInput;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.repository.CozinhaRepository;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @GetMapping
    public Page<CozinhaApiModel> listar(@PageableDefault(size=10) Pageable pageable) {
        
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<Cozinha> cozinhasList = cozinhasPage.getContent();
        List<CozinhaApiModel> cozinhasApiModel = cozinhaModelAssembler.toCollectionApiModel(cozinhasList);
        return new PageImpl<>(cozinhasApiModel, pageable, cozinhasPage.getTotalElements());
        
    }

    @GetMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaApiModel buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toApiModel(cadastroCozinhaService.buscar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaApiModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaModelAssembler.toDomainModel(cozinhaInput);
        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinha);
        return cozinhaModelAssembler.toApiModel(cozinhaSalva);
    }

    @PutMapping("{id}")
    public CozinhaApiModel atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscar(id);        
              
        cozinhaModelAssembler.copyToDomainModel(cozinhaInput, cozinhaAtual);
        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual);
        return cozinhaModelAssembler.toApiModel(cozinhaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        
        cadastroCozinhaService.remover(id);
    }

}
