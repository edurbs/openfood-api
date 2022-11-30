package com.edurbs.openfood.domain.repository;

import com.edurbs.openfood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto fotoProduto);
}
