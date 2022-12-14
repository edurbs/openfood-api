package com.edurbs.openfood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.edurbs.openfood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@ValorZeroIncluiDescricao(valorField="taxaFrete", descricaoField="nome", descricaoObrigatoria="Frete grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    
    @Column(nullable = false)
    private String nome;

    
    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private Boolean ativo=true;

    private Boolean aberto;

    @CreationTimestamp
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;
    
    @Embedded
    private Endereco endereco;

    
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name="restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    //private List<FormaPagamento> formasPagamento = new ArrayList<>();
    // para não permtir Formas de Pagamento repetidas
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name="restaurante_usuario_responsavel",
            joinColumns =  @JoinColumn(name="restaurante_id"),
            inverseJoinColumns = @JoinColumn(name="usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar(){
        setAtivo(true);
    }

    public void desativar(){
        setAtivo(false);
    }

    public boolean associarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().add(formaPagamento);
    }

    public boolean desassociarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().remove(formaPagamento);
    }

    public void associarResponsavel(Usuario usuario) {
        getResponsaveis().add(usuario);
    }

    public void desassociarResponsavel(Usuario usuario) {
        getResponsaveis().remove(usuario);
        
    }

    public void fechar() {
        setAberto(false);
    }

    public void abrir(){
        setAberto(true);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento){
        return !aceitaFormaPagamento(formaPagamento);
    }

}
