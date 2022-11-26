package com.edurbs.openfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    CRIADO("Criado"), 
    CONFIRMADO("Confirmado", CRIADO), 
    ENTREGUE("Entrege", CONFIRMADO), 
    CANCELADO("Cancelado", CRIADO, CONFIRMADO);

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores){
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public String getDescricao(){
        return this.descricao;
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus){
        return !novoStatus.statusAnteriores.contains(this);
    }


}
