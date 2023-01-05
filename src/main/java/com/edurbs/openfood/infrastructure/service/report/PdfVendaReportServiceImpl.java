package com.edurbs.openfood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.openfood.domain.exception.BusinessException;
import com.edurbs.openfood.domain.filter.VendaDiariaFilter;
import com.edurbs.openfood.domain.service.VendaQueryService;
import com.edurbs.openfood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {
    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper"); // from resource
    
            var parameters = new HashMap<String, Object>();
            
            var localePtBr = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
            parameters.put("REPORT_LOCALE", localePtBr);
    
            var vendiasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            
            var dataSource = new JRBeanCollectionDataSource(vendiasDiarias);
    
            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não possível emitir relatório de vendas diárias", e);
        }
    }
    
}
