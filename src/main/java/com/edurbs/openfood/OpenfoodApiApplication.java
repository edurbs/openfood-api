package com.edurbs.openfood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfoodApiApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));


	}

}
