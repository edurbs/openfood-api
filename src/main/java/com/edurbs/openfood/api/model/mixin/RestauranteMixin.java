package com.edurbs.openfood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Endereco;
import com.edurbs.openfood.domain.model.FormaPagamento;
import com.edurbs.openfood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class RestauranteMixin {
    
    @JsonIgnoreProperties(value="nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private Boolean ativo;

    @JsonIgnore
    private Boolean aberto;

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();    
}
