package com.edurbs.openfood.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.ColorType;

import org.hamcrest.Matcher;
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

import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Endereco;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.service.CadastroCidadeService;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;
import com.edurbs.openfood.domain.service.CadastroEstadoService;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;
import com.edurbs.openfood.util.DatabaseCleaner;
import com.edurbs.openfood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class RestauranteControllerTest {
    
    @LocalServerPort
	private int port;

    @Autowired
	private DatabaseCleaner databaseCleaner;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired 
    private CadastroEstadoService cadastroEstadoService;

    private final long RESTAURANTE_INEXISTENTE = 1000;



    private int quantidadeRestaurantes;

    private Restaurante restauranteTeste;

    @BeforeEach
	public void setUp(){
			RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
			RestAssured.port = port;
			RestAssured.basePath = "/restaurantes";		

			//flyway.migrate();

			databaseCleaner.clearTables();
			prepararDados();
	}

    
	private void prepararDados() {
		List<Restaurante> restaurantes = new ArrayList<>();

        Estado estado = new Estado();
        estado.setNome("Minas Gerais");
        estado = cadastroEstadoService.salvar(estado);


        Cidade cidade = new Cidade();
        cidade.setNome("Varginha");
        cidade.setEstado(estado);
        cidade = cadastroCidadeService.salvar(cidade);        

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Mineira");
        cozinha = cadastroCozinhaService.salvar(cozinha);

        Endereco endereco = new Endereco();
        endereco.setBairro("Centro");
        endereco.setCep("78690000");
        endereco.setCidade(cidade);
        endereco.setComplemento("Ap 1");
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("15");
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Vietnamita");
        restaurante.setAberto(true);
        restaurante.setAtivo(false);
        restaurante.setCozinha(cozinha);
        restaurante.setTaxaFrete(BigDecimal.valueOf(10L));
        restaurante.setEndereco(endereco);

		restaurantes.add(restaurante);

		

		restaurantes.forEach(cadastroRestauranteService::salvar);
		
		this.quantidadeRestaurantes=restaurantes.size();
		this.restauranteTeste=restaurante;

	}

    @Test
    void testBuscar() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", restauranteTeste.getId())
            .when()
                .get("/{id}")
            .then()
                .body("nome", Matchers.equalTo(restauranteTeste.getNome()))
                .statusCode(HttpStatus.OK.value());
        
        
    }

    @Test
    void testBuscar_NaoExistente(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", RESTAURANTE_INEXISTENTE)
            .when()
                .get("/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    void testListar() {
        RestAssured.given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.hasItem(restauranteTeste.getNome()))
                .body("", Matchers.hasSize(quantidadeRestaurantes));
        
    }

    @Test
    void testSalvar() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(ResourceUtils.getContentFromResource("/json/restaurante.json"))
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    void testSalvarComErro(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(ResourceUtils.getContentFromResource("/json/restauranteComErro.json"))
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
