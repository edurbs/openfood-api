package com.edurbs.openfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoApiModel {
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
