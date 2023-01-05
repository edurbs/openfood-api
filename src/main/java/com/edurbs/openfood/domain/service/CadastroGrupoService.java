package com.edurbs.openfood.domain.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.GrupoNaoEncontradoException;
import com.edurbs.openfood.domain.model.City;
import com.edurbs.openfood.domain.model.Grupo;
import com.edurbs.openfood.domain.model.Permissao;
import com.edurbs.openfood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void remover(Long id) {
        try {
            grupoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Grupo código %d está em uso", id));
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id); 
        }
        
    }

    public List<Grupo> listar(){
        return grupoRepository.findAll();
    }

    public Grupo buscar(Long id){
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    public Collection<Permissao> listarPermissoesPorGrupo(Long grupoId) {
        Grupo grupo = buscar(grupoId);
        return grupo.getPermissoes();
    }

    @Transactional
    public void associaPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscar(permissaoId);
        grupo.associaPermissao(permissao);
    }

    @Transactional
    public void desassociaPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscar(permissaoId);
        grupo.desassociaPermissao(permissao);
    }
}
