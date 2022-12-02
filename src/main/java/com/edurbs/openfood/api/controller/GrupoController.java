package com.edurbs.openfood.api.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.GrupoAssembler;
import com.edurbs.openfood.api.model.GrupoApiModel;
import com.edurbs.openfood.api.model.input.GrupoInput;
import com.edurbs.openfood.domain.exception.GrupoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.model.Grupo;
import com.edurbs.openfood.domain.service.CadastroGrupoService;




@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoAssembler grupoAssembler;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)    
    public List<GrupoApiModel> listar() {
        var lista = cadastroGrupoService.listar();        
        return grupoAssembler.toCollectionApiModel(lista);
    }

    @GetMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoApiModel buscar(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(httpServletRequest);
        try {
            Grupo grupo = cadastroGrupoService.buscar(id);
            return grupoAssembler.toApiModel(grupo);
        } catch (TypeMismatchException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoApiModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        try {
            
            Grupo grupo = grupoAssembler.toDomainModel(grupoInput);
            Grupo grupoSalvo = cadastroGrupoService.salvar(grupo);
            return grupoAssembler.toApiModel(grupoSalvo);
            
            
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoApiModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        try {
            Grupo grupoAtual = cadastroGrupoService.buscar(id);
            grupoAssembler.copyToDomainModel(grupoInput, grupoAtual);
            Grupo grupoSalvo = cadastroGrupoService.salvar(grupoAtual);
            return grupoAssembler.toApiModel(grupoSalvo);

        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }       
        
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroGrupoService.remover(id);        
    }

    
    
    
    
}
