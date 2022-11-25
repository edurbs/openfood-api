package com.edurbs.openfood.domain.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.CidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.exception.PedidoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.ProdutoNaoEncontradoException;
import com.edurbs.openfood.domain.exception.RestauranteNaoEncontradoException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.model.ItemPedido;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.model.StatusPedido;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.repository.ItemPedidoRepository;
import com.edurbs.openfood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired 
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscar(Long pedidoId) {        
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));        
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
     
        this.validaPedido(pedido);
        this.validaItensPedido(pedido);

        pedido.definirFrete();
        pedido.calcularValorTotal(); // e subtotal também
        
        pedido.setStatus(StatusPedido.CRIADO);

        var pedidoSalvo = pedidoRepository.save(pedido);

        pedidoSalvo.atribuirPedidoAosItens();

        return pedidoSalvo;
    }

    public void validaPedido(Pedido pedido) {
        var clienteId = pedido.getCliente().getId();
        var cliente = cadastroUsuarioService.buscar(clienteId);
        pedido.setCliente(cliente);
        
        var restauranteId = pedido.getRestaurante().getId();
        var restaurante = cadastroRestauranteService.buscar(restauranteId);
        pedido.setRestaurante(restaurante);
        
        var formasPagamentoAceitas = restaurante.getFormasPagamento();
        
        var formaPagamentoId = pedido.getFormaPagamento().getId(); 
        var formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        pedido.setFormaPagamento(formaPagamento);
        if(restauranteNaoAceitaFormaPagamento(formaPagamento, formasPagamentoAceitas)){
            throw new NegocioException(String.format("Restaurante código %d não aceita forma de pagamento código %d", restauranteId, formaPagamentoId));
        }       

        var cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        var cidade = cadastroCidadeService.buscar(cidadeId);
        pedido.getEnderecoEntrega().setCidade(cidade);
    }

    public void validaItensPedido(Pedido pedido){
        var restaurante = pedido.getRestaurante();
        var restauranteId= restaurante.getId();
        var produtosDoRestaurante = restaurante.getProdutos();
        var itens = pedido.getItens();

        itens.forEach(item -> {
            var produtoId = item.getProduto().getId();
            var produto = cadastroProdutoService.buscar(produtoId);

            if(restauranteNaoContemProduto(produto, produtosDoRestaurante)){
                throw new NegocioException(String.format("Produto código %d não está cadastradado no restaurante código %d", produtoId, restauranteId));
            }

            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.calcularPrecoTotal();
            //itemPedidoRepository.save(item); 
        });
    }


    public boolean restauranteNaoContemProduto(Produto produto, List<Produto> produtos) {       
        return !produtos.contains(produto);
    }

    public boolean restauranteNaoAceitaFormaPagamento(FormaPagamento formaPagamento, Set<FormaPagamento> formasPagamentoAceitas){
        return !formasPagamentoAceitas.contains(formaPagamento);
    }


}
