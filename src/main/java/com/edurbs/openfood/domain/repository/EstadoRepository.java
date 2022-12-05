package com.edurbs.openfood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edurbs.openfood.domain.model.Estado;

//@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
