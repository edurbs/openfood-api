package com.edurbs.openfood.core.openapi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
@EnableWebMvc
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("OpenFood API")
                        .description("OpenFood application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://api.openfood.local")))
                .externalDocs(new ExternalDocumentation()
                        .description("OpenFood Wiki Documentation")
                        .url("https://github.org/edurbs/openfood-api"));

    }

    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> {
                Operation get = pathItem.getGet();
                if(get != null){

                    ApiResponses apiGetResponses = get.getResponses();
                    this.getGetResponse().forEach(apiGetResponses::put);
                }
            });
        };
    }


    private HashMap<String, ApiResponse> getGetResponse() {
        HashMap<String, ApiResponse> map = new HashMap<>();

        map.put(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new ApiResponse()
                        .description("Erro interno do Servidor")
                        .content(new Content()
                                .addMediaType(
                                        MediaType.APPLICATION_JSON_VALUE,
                                        new io.swagger.v3.oas.models.media.MediaType())));

        map.put(
                HttpStatus.NOT_ACCEPTABLE.toString(),
                new ApiResponse()
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .content(new Content()
                                .addMediaType(
                                        MediaType.APPLICATION_JSON_VALUE,
                                        new io.swagger.v3.oas.models.media.MediaType())));

        return map;
    }

}
