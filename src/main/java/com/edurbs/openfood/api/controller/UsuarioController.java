package com.edurbs.openfood.api.controller;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.GrupoAssembler;
import com.edurbs.openfood.api.assembler.UsuarioAssembler;
import com.edurbs.openfood.api.model.GrupoApiModel;
import com.edurbs.openfood.api.model.UsuarioApiModel;
import com.edurbs.openfood.api.model.input.UsuarioAdicionarInput;
import com.edurbs.openfood.api.model.input.UsuarioAtualizarInput;
import com.edurbs.openfood.api.model.input.UsuarioSenhaInput;
import com.edurbs.openfood.domain.exception.GrupoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.exception.UsuarioNaoEncontradoException;
import com.edurbs.openfood.domain.model.Grupo;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.service.CadastroUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name = "Usuários", description = "Gerencia os usuários")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private GrupoAssembler grupoAssembler;

        @Operation(summary = "Lista todos usuários")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioApiModel> listar() {
        var lista = cadastroUsuarioService.listar();
        return usuarioAssembler.toCollectionApiModel(lista);
    }

        @Operation(summary = "Busca um usuário por ID")
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

        @Operation(summary = "Cadastra um usuário")
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

        @Operation(summary = "Atualiza um usuário por ID")
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
    

        @Operation(summary = "Exclui um usuário")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroUsuarioService.remover(id);
    }

        @Operation(summary = "Altera a senha de um usuário")
    @PutMapping(value="/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioSenhaInput usuarioSenhaInput) {
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

        @Operation(summary = "Lista os grupos de um usuário por ID")
    @GetMapping(value="/{usuarioId}/grupos")
    @ResponseStatus(HttpStatus.OK)
    public List<GrupoApiModel> listarGrupos(@PathVariable Long usuarioId) {
        Collection<Grupo> grupos =  cadastroUsuarioService.listarGrupos(usuarioId);
        return grupoAssembler.toCollectionApiModel(grupos);
    }

    @Operation(summary = "Associa um grupo a um usuário por ID")
    @PutMapping(value="/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);        
    }

        @Operation(summary = "Desassocia um grupo de um usuário por ID")
    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
    }
    
    
}
