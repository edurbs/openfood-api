package com.edurbs.openfood.domain.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.exception.UsuarioNaoEncontradoException;
import com.edurbs.openfood.domain.model.Grupo;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
    

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {   

        // para não salvar 2 vezes (a primeira seria quando fizer o findByEmail visto estar gerenciado pelo JPA)
        usuarioRepository.detach(usuario); 
        
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){
            throw new NegocioException(String.format("Já existe um usuário com o email %s", usuario.getEmail()));
        }
        
        return usuarioRepository.save(usuario);
    }

    public void remover(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Usuário código %d em uso", id));
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        }


    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String senhaNova){
        var usuario = buscar(usuarioId);
        if(usuario.senhaNaoCoincideCom(senhaAtual)){
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }else if(usuario.senhaCoincideCom(senhaNova)){
            throw new NegocioException("Nova senha não pode ser igual a senha atual.");
        }
        usuario.setSenha(senhaNova);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        Usuario usuario = buscar(usuarioId);
        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        Usuario usuario = buscar(usuarioId);
        usuario.desassociarGrupo(grupo);
        
    }

    public Collection<Grupo> listarGrupos(Long usuarioId) {
        Usuario usuario = buscar(usuarioId);
        return usuario.getGrupos();
    }
}
