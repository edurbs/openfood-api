package com.edurbs.openfood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;

import com.edurbs.openfood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidosFilter")
@Getter
@Setter
public class PedidoApiModel {
    
    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private StatusPedido status;
    
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataEntrega;

    private OffsetDateTime dataCancelamento;

    private RestauranteResumidoApiModel restaurante;

    private UsuarioApiModel cliente;

    @Embedded
    private EnderecoApiModel enderecoEntrega;

    private FormaPagamentoApiModel formaPagamento;

    private List<ItemPedidoApiModel> itens = new ArrayList<>();

}
