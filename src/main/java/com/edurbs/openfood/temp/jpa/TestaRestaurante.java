package com.edurbs.openfood.temp.jpa;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.edurbs.openfood.OpenfoodApiApplication;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.repository.CozinhaRepository;
import com.edurbs.openfood.domain.repository.RestauranteRepository;

public class TestaRestaurante {
    public static void main(String[] args) {
        ApplicationContext app = new SpringApplicationBuilder(OpenfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        
        RestauranteRepository restauranteRepository = app.getBean(RestauranteRepository.class);

        CozinhaRepository cozinhaRepository =app.getBean(CozinhaRepository.class);

        Restaurante r1 = new Restaurante();
        r1.setNome("Da mamãe");
        r1.setTaxaFrete(BigDecimal.valueOf(5L));
        r1.setAtivo(true);
        r1.setDataCadastro(OffsetDateTime.now());
        
        r1.setCozinha(cozinhaRepository.buscar(2L));

        restauranteRepository.salvar(r1);

        restauranteRepository.listar().forEach(
            r -> System.out.printf("%s - %f - %s %n", 
                    r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome()));

    }
}
