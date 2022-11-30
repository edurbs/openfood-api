package com.edurbs.openfood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
    
    InputStream recuperar (String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto){
        if(nomeArquivoAntigo != null){
            excluir(nomeArquivoAntigo);
        }
        armazenar(novaFoto);
    }

    void excluir(String nomeArquivoAntigo);

    default String gerarNomeArquivo(String nomeArquivo){
        return UUID.randomUUID()+"_"+nomeArquivo;
    }

    @Getter
    @Builder
    public class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
