package com.edurbs.openfood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface StorageFotoService {
    
    void armazenar(NovaFoto novaFoto);

    @Getter
    @Builder
    public class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
