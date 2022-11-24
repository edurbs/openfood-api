package com.edurbs.openfood.api.controller;

import java.util.Collection;
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

import com.edurbs.openfood.api.assembler.PermissaoAssembler;
import com.edurbs.openfood.api.model.PermissaoApiModel;
import com.edurbs.openfood.domain.model.Grupo;
import com.edurbs.openfood.domain.model.Permissao;
import com.edurbs.openfood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    

    @Autowired
    private PermissaoAssembler permissaoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PermissaoApiModel> listarPermissoesDoGrupo(@PathVariable Long grupoId) {
        Collection<Permissao> permissoes = cadastroGrupoService.listarPermissoesPorGrupo(grupoId);
        return permissaoAssembler.toCollecApiModel(permissoes);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associaPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.associaPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociaPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociaPermissao(grupoId, permissaoId);
    }
}
