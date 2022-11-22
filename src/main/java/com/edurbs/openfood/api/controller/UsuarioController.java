package com.edurbs.openfood.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.UsuarioAssembler;
import com.edurbs.openfood.api.model.UsuarioApiModel;
import com.edurbs.openfood.api.model.input.UsuarioAdicionarInput;
import com.edurbs.openfood.api.model.input.UsuarioAtualizarInput;
import com.edurbs.openfood.api.model.input.UsuarioSenhaInput;
import com.edurbs.openfood.domain.exception.GrupoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.exception.UsuarioNaoEncontradoException;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.service.CadastroUsuarioService;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioApiModel> listar() {
        var lista = cadastroUsuarioService.listar();
        return usuarioAssembler.toCollectionApiModel(lista);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioApiModel buscar(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(httpServletRequest);
        try {
            Usuario usuario = cadastroUsuarioService.buscar(id);
            return usuarioAssembler.toApiModel(usuario) ;
        } catch (TypeMismatchException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioApiModel adicionar(@RequestBody @Valid UsuarioAdicionarInput usuarioAdicionarInput) {
        try {
            Usuario usuario = usuarioAssembler.toDomainModel(usuarioAdicionarInput);
            Usuario usuarioSalvo = cadastroUsuarioService.salvar(usuario);
            return usuarioAssembler.toApiModel(usuarioSalvo);
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioApiModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioAtualizarInput usuarioAtualizarInput) {
        try {
            Usuario usuarioAtual = cadastroUsuarioService.buscar(id);
            usuarioAssembler.copyToDomainModel(usuarioAtualizarInput, usuarioAtual);
            Usuario usuarioSalvo = cadastroUsuarioService.salvar(usuarioAtual);
            return usuarioAssembler.toApiModel(usuarioSalvo);
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
        
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroUsuarioService.remover(id);
    }

    @PutMapping(value="/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putMethodName(@PathVariable Long id, @RequestBody @Valid UsuarioSenhaInput usuarioSenhaInput) {
        try {
            cadastroUsuarioService.alterarSenha(
                    id, 
                    usuarioSenhaInput.getSenhaAtual(),
                    usuarioSenhaInput.getSenhaNova()
                );
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getLocalizedMessage());
        }


        
    }
    
}
