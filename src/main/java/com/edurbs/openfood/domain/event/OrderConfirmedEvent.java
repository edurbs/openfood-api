package com.edurbs.openfood.domain.event;

import com.edurbs.openfood.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class OrderConfirmedEvent {
    private Pedido pedido;
}
