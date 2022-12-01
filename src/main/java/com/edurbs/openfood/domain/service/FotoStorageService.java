package com.edurbs.openfood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
    
    FotoRecuperada recuperar (String nomeArquivo);

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
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private Long size;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl(){
            return url != null;
        }
        public boolean temInputStream(){
            return inputStream != null;
        }
    }
}
