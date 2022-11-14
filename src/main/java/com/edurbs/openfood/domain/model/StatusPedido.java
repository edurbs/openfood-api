package com.edurbs.openfood.domain.model;

import javax.persistence.Entity;

@Entity
public enum StatusPedido {
    CRIADO, CONFIRMADO, ENTREGUE, CANCELADO;
}
