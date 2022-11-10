package com.edurbs.openfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Restaurante> todos() {
        return entityManager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurante adicionar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }

    @Override
    public Restaurante porId(Long id) {

        return entityManager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public void remover(Restaurante restaurante) {
        Restaurante restauranteDoBanco = porId(restaurante.getId());
        entityManager.remove(restauranteDoBanco);

    }

}
