package com.edurbs.openfood.infrastructure.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.edurbs.openfood.core.storage.StorageProperties;
import com.edurbs.openfood.domain.service.FotoStorageService;


//@Service
public class LocalStorageFotoService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;
    
    @Override
    public void armazenar(NovaFoto novaFoto) {
    
        InputStream inputStream = novaFoto.getInputStream();
        String nomeArquivo = novaFoto.getNomeArquivo();

        Path path = getPath(nomeArquivo);

        try {
            FileCopyUtils.copy(inputStream, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Não foi possível salvar a foto", e);            
        }
        
    }

    public Path getPath(String nomeArquivo){
        return storageProperties.getLocal().getPhotoFolder().resolve(nomeArquivo);
    }

    @Override
    public void excluir(String nomeDoArquivoAntigo) {
        
        Path path = getPath(nomeDoArquivoAntigo);

        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Erro ao excluir foto.", e);
        }
        
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        var pathNomeArquivo = getPath(nomeArquivo);
        try {
            return Files.newInputStream(pathNomeArquivo);
        } catch (Exception e) {
            throw new StorageException("Não foi possível ler o arquivo.", e);
        }

        
        
       
    }

    

    
    
}
