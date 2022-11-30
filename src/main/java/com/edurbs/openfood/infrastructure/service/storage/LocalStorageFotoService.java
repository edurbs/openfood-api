package com.edurbs.openfood.infrastructure.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.edurbs.openfood.domain.exception.StorageException;
import com.edurbs.openfood.domain.service.StorageFotoService;


@Service
public class LocalStorageFotoService implements StorageFotoService {

    @Value("${openfood.storage.photo.folder}")
    private Path photoFolder;
    
    @Override
    public void armazenar(NovaFoto novaFoto) {
    
        InputStream inputStream = novaFoto.getInputStream();
        String nomeArquivo = novaFoto.getNomeArquivo();

        Path path = photoFolder.resolve(nomeArquivo);

        try {
            FileCopyUtils.copy(inputStream, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Não foi possível salvar a foto", e);            
        }
        
    }

    public Path fullPath(String nomeArquivo){
        return photoFolder.resolve(nomeArquivo);
    }

    

    
    
}
