package com.edurbs.openfood.temp.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.edurbs.openfood.OpenfoodApiApplication;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.repository.CidadeRepository;
import com.edurbs.openfood.domain.repository.EstadoRepository;

public class TestaExercicio1 {
    public static void main(String[] args) {
        ApplicationContext app = new SpringApplicationBuilder(OpenfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CidadeRepository cidadeRepository = app.getBean(CidadeRepository.class);
        EstadoRepository estadoRepository = app.getBean(EstadoRepository.class);

        Estado estadoMT = new Estado();
        estadoMT.setNome("Mato Grosso");
        estadoMT = estadoRepository.salvar(estadoMT);

        Cidade cidade1 = new Cidade();
        cidade1.setNome("Nova Xavantina");
        cidade1.setEstado(estadoMT);
        cidade1 = cidadeRepository.salvar(cidade1);

        Cidade cidade2 = new Cidade();
        cidade2.setNome("Barra do Garças");
        cidade2.setEstado(estadoMT);
        cidade2 = cidadeRepository.salvar(cidade2);

        Cidade cidade3 = new Cidade();
        cidade3.setNome("Campinápolis");
        cidade3.setEstado(estadoMT);
        cidade3 = cidadeRepository.salvar(cidade3);


        cidadeRepository.remover(cidade2);

        cidadeRepository.listar()
                .stream()
                .forEach(cidade -> System.out.printf("Cidade %s está em %s %n", cidade.getNome(), cidade.getEstado().getNome()));

    }
}
