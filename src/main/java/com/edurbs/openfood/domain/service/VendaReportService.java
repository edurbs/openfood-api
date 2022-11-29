package com.edurbs.openfood.domain.service;

import com.edurbs.openfood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
