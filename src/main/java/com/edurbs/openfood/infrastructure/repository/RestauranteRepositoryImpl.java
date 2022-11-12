package com.edurbs.openfood.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> listar() {
        return entityManager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }

    @Override
    public Restaurante buscar(Long id) {

        return entityManager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        // Restaurante restauranteDoBanco = buscar(id);
        // entityManager.remove(restauranteDoBanco);

        Optional.ofNullable(buscar(id))
                .ifPresentOrElse(
                        restauranteDoBanco -> entityManager.remove(restauranteDoBanco),
                        () -> {
                            throw new EmptyResultDataAccessException(1);
                        });

    }

}
