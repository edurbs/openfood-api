package com.edurbs.openfood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.edurbs.openfood.core.storage.StorageProperties.TypeStorage;
import com.edurbs.openfood.domain.service.FotoStorageService;
import com.edurbs.openfood.infrastructure.service.storage.LocalStorageFotoService;
import com.edurbs.openfood.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name="openfood.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3(){
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getId(), 
                storageProperties.getS3().getKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService(){
        if(TypeStorage.S3.equals(storageProperties.getType())){
            return new LocalStorageFotoService();
        }else{
            return new S3FotoStorageService();
        }
    }
}
