package com.edurbs.openfood.core.openapi;

import java.util.HashMap;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.edurbs.openfood.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
@EnableWebMvc
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {

        TypeResolver typeResolver = new TypeResolver();

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
            var resolvedSchema = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(Problem.class));
            openApi.schema( resolvedSchema.schema.getName(), resolvedSchema.schema);

            openApi.getPaths().values().forEach(pathItem -> 
                pathItem.readOperationsMap().forEach((httpMethod, operation) -> {
                    ApiResponses responses = operation.getResponses();
                    switch (httpMethod) {
                        case GET:
                            this.getGetResponses().forEach(responses::put);
                            break;        
                        case POST:
                            this.getPostResponses().forEach(responses::put);
                            break;
                        case PUT:
                            this.getPutResponses().forEach(responses::put);
                            break;
                        case DELETE:
                            this.getDeleteResponses().forEach(responses::put);
                            break;
                        default:
                            break;
                    }
                }));
        };
    }

    private ApiResponse getApiResponse(String description){
        var content = new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE, 
                        new io.swagger.v3.oas.models.media.MediaType());
        return new ApiResponse()
                .description(description)
                .content(content);
    }

    private HashMap<String, ApiResponse> getGetResponses() {
        HashMap<String, ApiResponse> map = new HashMap<>();

        map.put(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
               getApiResponse("Erro interno do servidor)"));

        map.put(HttpStatus.NOT_ACCEPTABLE.toString(),
                getApiResponse("Recurso não possui representação que pode ser aceita pelo consumidor"));

        return map;
    }

    private HashMap<String, ApiResponse> getPostResponses(){

        HashMap<String, ApiResponse> map = getDeleteResponses();

        map.put(HttpStatus.NOT_ACCEPTABLE.toString(),
                getApiResponse("Recurso não possui representação que poderia ser aceita pelo consumidor"));
        
        map.put(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(),
                getApiResponse("Requisição recusada porque o corpo está em um formato não suportado"));

        return map;
    }

    private HashMap<String, ApiResponse> getPutResponses(){
        return getPostResponses();
    }

    private HashMap<String, ApiResponse> getDeleteResponses(){
        HashMap<String, ApiResponse> map = new HashMap<>();
        map.put(HttpStatus.BAD_REQUEST.toString(),
                getApiResponse("Requisição inválida (erro do cliente)"));

        map.put(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                getApiResponse("Erro interno no servidor"));
        return getPostResponses();
    }
}
