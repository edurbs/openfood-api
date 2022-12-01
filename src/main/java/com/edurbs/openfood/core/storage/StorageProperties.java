package com.edurbs.openfood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("openfood.storage")
public class StorageProperties {
    private Local local = new Local();
    private S3 s3 = new S3();

    @Getter
    @Setter
    public class Local {
        private Path photoFolder;
    }

    @Getter
    @Setter
    public class S3 {
        private String id;
        private String key;
        private String bucket;
        private Regions regiao;
        private String folder;
    }
}
