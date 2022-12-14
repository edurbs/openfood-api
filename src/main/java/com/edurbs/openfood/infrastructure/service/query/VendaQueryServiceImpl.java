package com.edurbs.openfood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.filter.VendaDiariaFilter;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.model.StatusPedido;
import com.edurbs.openfood.domain.model.dto.VendaDiaria;
import com.edurbs.openfood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        String dataCriacao = "dataCriacao";
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        List<Predicate> predicates = new ArrayList<>();
        
        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }
        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(dataCriacao), filtro.getDataCriacaoInicio()));
        }   
        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(dataCriacao), filtro.getDataCriacaoFim()));
        }
        
        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        var functionConverTzDataCriacao = builder.function("convert_tz", Date.class, 
                root.get(dataCriacao), 
                builder.literal("+00:00"), 
                builder.literal(timeOffset));
        
        var functionDateDataCriacao = builder.function("date", Date.class, functionConverTzDataCriacao);
        
        var selection = builder.construct(VendaDiaria.class, 
                functionDateDataCriacao, 
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
        
        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));        
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
    
}
