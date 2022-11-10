package com.edurbs.openfood.temp.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.edurbs.openfood.OpenfoodApiApplication;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext app = new SpringApplicationBuilder(OpenfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = app.getBean(CozinhaRepository.class);

        cozinhaRepository.todas()
                .stream()
                .forEach(s -> System.out.println(s.getNome()));

        Cozinha c1 = new Cozinha();
        c1.setNome("Mexicana");

        Cozinha c2 = new Cozinha();
        c2.setNome("Japonesa");

        var cs1=cozinhaRepository.adicionar(c1);
        var cs2=cozinhaRepository.adicionar(c2);

        System.out.printf("%d - %s %n", cs1.getId(), cs1.getNome());
        System.out.printf("%d - %s %n", cs2.getId(), cs2.getNome());

        Cozinha busca = cozinhaRepository.porId(1L);
        System.out.println(busca.getNome());

        cs1.setNome("Argentina");
        Cozinha cs1Alterado = cozinhaRepository.adicionar(cs1);
        System.out.println(cozinhaRepository.porId(cs1Alterado.getId()).getNome());

        Cozinha cRemover = new Cozinha();
        cRemover.setId(1L);
        cozinhaRepository.remover(cRemover);
        

    }
}
