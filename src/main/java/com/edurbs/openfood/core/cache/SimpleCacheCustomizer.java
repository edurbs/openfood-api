package com.edurbs.openfood.core.cache;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

public class SimpleCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        // TODO Auto-generated method stub
        
    }

    
    
}
