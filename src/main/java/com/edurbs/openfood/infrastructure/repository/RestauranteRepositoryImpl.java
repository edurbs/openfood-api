package com.edurbs.openfood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.edurbs.openfood.core.validation.TaxaFrete;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.RestauranteRepository;
import com.edurbs.openfood.domain.repository.RestauranteRepositoryQueries;
import com.edurbs.openfood.infrastructure.repository.specifications.RestauranteSpecs;



@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> consultaPorNomeComCriteriaAPI(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)){
            Predicate nomPredicate = criteriaBuilder.like(root.get("nome"), "%" + nome +"%");        
            predicates.add(nomPredicate);
        }
        if(taxaInicial != null){
            Predicate taxaInicialPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial);
            predicates.add(taxaInicialPredicate);
        }
        if(taxaFinal != null){
            Predicate taxaFinalPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal);
            predicates.add(taxaFinalPredicate);
        }

        //criteriaQuery.where(nomPredicate, taxaInicialPredicate, taxaFinalPredicate);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();

    }

    public List<Restaurante> consultaPorNomeViaSDJCustomizado(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        /*
        var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal"; */

        var parametros = new HashMap<String, Object>();
                
        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0=0 ");
        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%"+nome+"%");
        }
        if (taxaInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaInicial);
        }
        if (taxaFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFinal);
        }
        
        TypedQuery<Restaurante> typeQuery = entityManager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> typeQuery.setParameter(chave, valor));
        
        return typeQuery.getResultList();


        /*return entityManager.createQuery(jpql.toString(), Restaurante.class)
                .setParameter("nome", "%"+nome+"%")
                .setParameter("taxaInicial", taxaInicial)
                .setParameter("taxaFinal", taxaFinal)
                .getResultList();*/
                
    }

    public List<Restaurante> listarAtivosComFreteGratis(){
        /*var comFreteGratis = new RestauranteComFreteGratisSpec();
        var ativos = new RestauranteAtivosSpec();
        return restauranteRepository.findAll(comFreteGratis.and(ativos));        */

        return restauranteRepository.findAll(RestauranteSpecs.fromFreteGratis().and(RestauranteSpecs.ativos()));
    }
}
