package com.edurbs.openfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class OpenfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfoodApiApplication.class, args);



	}

}
