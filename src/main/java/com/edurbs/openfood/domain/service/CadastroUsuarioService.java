package com.edurbs.openfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.exception.UsuarioNaoEncontradoException;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
    

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {        
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
}
