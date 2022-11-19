package com.edurbs.openfood;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;
import com.edurbs.openfood.util.DatabaseCleaner;
import com.edurbs.openfood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhasIT {
    
    
	private static final int ID_INEXISTENTE = 100;
	private int quantidadeCozinhas;
	private Cozinha cozinhaTeste;

	@LocalServerPort
	private int port;

	// @Autowired
	// private Flyway flyway;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@BeforeEach
	public void setUp(){
			RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
			RestAssured.port = port;
			RestAssured.basePath = "/cozinhas";		

			//flyway.migrate();

			databaseCleaner.clearTables();
			prepararDados();
	}

    
	private void prepararDados() {
		PodamFactory podamFactory = new PodamFactoryImpl();
		
		Cozinha c1 = podamFactory.manufacturePojo(Cozinha.class);
		

		List<Cozinha> cozinhas = new ArrayList<>();
		
		// Cozinha c1 = new Cozinha();
		// c1.setNome("Vietnamita");
		// cozinhas.add(c1);

		// Cozinha c2 = new Cozinha();
		// c2.setNome("Russa");
		// cozinhas.add(c2);

		// Cozinha c3 = new Cozinha();
		// c3.setNome("Palestina");
		// cozinhas.add(c3);

		cozinhas.forEach(cadastroCozinhaService::salvar);

		
		
		
		this.quantidadeCozinhas=cozinhas.size();
		this.cozinhaTeste=c1;

	}

	@Test
	public void shouldReturnStatus200_whenConsultCozinhas(){
		RestAssured.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}

    @Test
	public void shouldReturnNCozinhas_whenConsultCozinhaTeste(){
		RestAssured.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(quantidadeCozinhas))
                .body("nome", Matchers.hasItem(cozinhaTeste.getNome()));
	}

	@Test
	public void shouldReturnStatus200_whenAddNewCozinha(){
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				//.body("{\"nome\":\"Japonesa\"}")
				.body(ResourceUtils.getContentFromResource("/json/cozinha.json"))
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void shouldReturnOK_whenConsultCozinha1(){
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", cozinhaTeste.getId())
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", Matchers.equalTo(cozinhaTeste.getNome()));

	}

	@Test
	public void shouldReturn404_whenConsultCozinha100(){
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("id", ID_INEXISTENTE)
			.when()
				.get("/{id}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());				
	}
	



}
