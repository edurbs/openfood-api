package com.edurbs.openfood.domain.model;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.transaction.TransactionScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {
    
    @Id
    @Column(name = "produto_id")
    @NotNull
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    @NotBlank
    @Size(max = 150)
    private String nomeArquivo;

    @Size(max = 150)
    private String descricao;

    @NotBlank
    @Size(max = 80)
    private String contentType;

    @NotNull    
    private Long tamanho;

    @Transient
    private InputStream inputStream;

    @Transient
    private Long restauranteId;

    public boolean restauranteNaoPossuiProduto(){
        var produtoRestauranteId = this.getProduto().getRestaurante().getId();
        return produtoRestauranteId != restauranteId;
    }
}
