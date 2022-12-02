package com.edurbs.openfood.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.edurbs.openfood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeApiModel {

    @NotBlank
    private String nome;

    private Estado estado;
}
