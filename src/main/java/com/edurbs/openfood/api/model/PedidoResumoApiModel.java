package com.edurbs.openfood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.edurbs.openfood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoApiModel {
    
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumidoApiModel restaurante;
    //private UsuarioApiModel cliente;
    private String nomeCliente;
}
