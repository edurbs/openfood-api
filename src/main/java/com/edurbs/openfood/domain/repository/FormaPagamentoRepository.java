package com.edurbs.openfood.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>  {
   
    @Query("select max(dateUpdated) from FormaPagamento")
    Optional<OffsetDateTime> getLastDateUpdated();

    @Query("select dateUpdated from FormaPagamento where id= :formaPagamentoId ")
    Optional<OffsetDateTime> getLastDateUpdatedById(Long formaPagamentoId);
}
