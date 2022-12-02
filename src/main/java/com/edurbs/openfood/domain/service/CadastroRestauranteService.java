package com.edurbs.openfood.domain.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.RestauranteNaoEncontradoException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    private static final String RESTAURANTE_EM_USO = "Restaurante código %d não pode ser removido, pois está em uso";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        
        Cozinha cozinha = cadastroCozinhaService.buscar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscar(cidadeId);
        
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(RESTAURANTE_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);

        }
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
        //return restauranteRepository.consultaPorNome("a", 1L);
        //return restauranteRepository.consultaPorNomeViaXML("ar", 1L);
        //return restauranteRepository.consultaPorNomeViaSDJCustomizado("a", BigDecimal.valueOf(1L), BigDecimal.valueOf(10L));
    }


    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restauranteAtual = buscar(restauranteId);
        restauranteAtual.ativar();
        // não precisa salvar porque está sendo gerenciado pelo JPA
    }

    @Transactional
    public void desativar(Long restauranteId){
        Restaurante restauranteAtual = buscar(restauranteId);
        restauranteAtual.desativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void desativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::desativar);
    }

    public List<Restaurante> listarAtivosComFreteGratis() {

        return restauranteRepository.listarAtivosComFreteGratis();
        
    }

    public Restaurante buscaPrimeiro() {
        var result = restauranteRepository.buscarPrimeiro();
        // if(result.isPresent()){
        //     return result.get();
        // }
        // return null;

        return result.orElseThrow(() -> new RestauranteNaoEncontradoException("Não há restaurantes cadastrados."));
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = buscar(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        restaurante.associarFormaPagamento(formaPagamento);
        
        
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = buscar(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        restaurante.desassociarFormaPagamento(formaPagamento);
     
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        var usuario = cadastroUsuarioService.buscar(usuarioId);
        var restaurante = buscar(restauranteId);
        restaurante.associarResponsavel(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        var usuario = cadastroUsuarioService.buscar(usuarioId);
        var restaurante = buscar(restauranteId);
        restaurante.desassociarResponsavel(usuario);
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.fechar();
        
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.abrir();
    }
}
