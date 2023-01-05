package com.edurbs.openfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edurbs.openfood.domain.model.City;

//@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
