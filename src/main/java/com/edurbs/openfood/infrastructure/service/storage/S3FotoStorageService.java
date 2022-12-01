package com.edurbs.openfood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.edurbs.openfood.core.storage.StorageProperties;
import com.edurbs.openfood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService{

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
    
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());
            objectMetadata.setContentLength(novaFoto.getSize());
    
            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(), 
                    caminhoArquivo, 
                    novaFoto.getInputStream(), 
                    objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
            
            amazonS3.putObject(putObjectRequest);
            
        } catch (Exception e) {
            throw new StorageException("Não foi possível a foto para Amazon S3", e);
            
        }
        
    }

    private String getCaminhoArquivo(String nomeArquivo){
        return String.format("%s/%s", storageProperties.getS3().getFolder(), nomeArquivo);
    }

    @Override
    public void excluir(String nomeArquivoAntigo) {
        // TODO Auto-generated method stub
        
    }
    
}
