package com.edurbs.openfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>  {

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")    
    List<Restaurante> consultaPorNome(@Param("nome") String nome, @Param("id") Long id);
    
    // resources/META-INF/orm.xml
    List<Restaurante> consultaPorNomeViaXML(@Param("nome") String nome, @Param("id") Long id);


    

    
    
}
