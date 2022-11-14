package com.edurbs.openfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;

    private LocalDateTime dataEntrega;

    @Embedded
    @Column(nullable = false)
    private Endereco enderecoEntrega;

    @ManyToOne
    @Column(nullable = false)
    private Usuario cliente;

    @ManyToOne
    @Column(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @Column(nullable = false)
    private StatusPedido statusPedido;

    @OneToMany(mappedBy = "pedido")
    @Column(nullable = false)
    private List<ItemPedido> itens;


}
