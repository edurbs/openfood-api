package com.edurbs.openfood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;


@Configuration
@EnableWebMvc
public class SpringDocConfig {

    // @Bean
    // public OpenAPI infoApi(){
    //   return new OpenAPI();
    // }

  // @Bean
  // public Docket apiDocket() {
  //   return new Docket(DocumentationType.OAS_30)
  //       .select()
  //         .apis(RequestHandlerSelectors.any())
  //         .build();
  // }
}
  
