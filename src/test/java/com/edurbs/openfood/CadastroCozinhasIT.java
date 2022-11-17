package com.edurbs.openfood;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CadastroCozinhasIT {
    
    @LocalServerPort
	private int port;

    
	@Test
	public void shouldReturnStatus200_whenConsultCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given()
				.basePath("/cozinhas")
				.port(port)
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}

    @Test
	public void shouldReturn10Cozinhas_whenConsultCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.given()
				.basePath("/cozinhas")
				.port(port)
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(10))
                .body("nome", Matchers.hasItem("Marroquina"));
	}

}
