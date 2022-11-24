package com.edurbs.openfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.edurbs.openfood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
    List<Restaurante> consultaPorNomeViaSDJCustomizado(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> listarAtivosComFreteGratis();
    
}
