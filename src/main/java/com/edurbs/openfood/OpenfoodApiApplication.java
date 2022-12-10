package com.edurbs.openfood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.edurbs.openfood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
@EnableCaching
public class OpenfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfoodApiApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
