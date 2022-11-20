package com.edurbs.openfood.api.model.mixin;

import java.time.OffsetDateTime;
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
    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Endereco endereco;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private Boolean ativo;

    @JsonIgnore
    private Boolean aberto;

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();    
}
