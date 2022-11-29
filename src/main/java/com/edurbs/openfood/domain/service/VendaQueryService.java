package com.edurbs.openfood.domain.service;

import java.util.List;

import com.edurbs.openfood.domain.filter.VendaDiariaFilter;
import com.edurbs.openfood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
