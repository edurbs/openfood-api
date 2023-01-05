package com.edurbs.openfood.api.util;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {
    
    public static void addUriResponseHeader(Object resourceId) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();

        Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).ifPresent(
                requestAttributes -> Optional.ofNullable(requestAttributes.getResponse()).ifPresent(
                        response -> response.setHeader(HttpHeaders.LOCATION, uri.toString())));
    }
}
