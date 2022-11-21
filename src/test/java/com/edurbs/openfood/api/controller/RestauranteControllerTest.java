package com.edurbs.openfood.api.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

import com.edurbs.openfood.api.model.input.CozinhaIdInput;
import com.edurbs.openfood.api.model.input.RestauranteInput;
import com.edurbs.openfood.domain.service.CadastroCidadeService;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;
import com.edurbs.openfood.domain.service.CadastroEstadoService;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;
import com.edurbs.openfood.util.DatabaseCleaner;
import com.edurbs.openfood.util.ResourceUtils;
import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class RestauranteControllerTest {
    /*
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

    private final long RESTAURANTEInput_INEXISTENTE = 1000;

    private Faker faker = new Faker(new Locale("pt_BR"));



    private int quantidadeRestauranteInputs;

    private RestauranteInput restauranteInputTeste;

    @BeforeEach
	public void setUp(){
			RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
			RestAssured.port = port;
			RestAssured.basePath = "/restauranteInputs";		

			//flyway.migrate();

			databaseCleaner.clearTables();
			prepararDados();
	}

    
	private void prepararDados() {
		//List<RestauranteInput> restauranteInputs = new ArrayList<>();
        
        // Estado estado = new Estado();
        // estado.setNome("Minas Gerais");
        // estado = cadastroEstadoService.salvar(estado);


        // Cidade cidade = new Cidade();
        // cidade.setNome("Varginha");
        // cidade.setEstado(estado);
        // cidade = cadastroCidadeService.salvar(cidade);        

        // CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
        // cozinhaIdInput.setNome("Mineira");
        // cozinhaIdInput = cadastroCozinhaIdInputService.salvar(cozinhaIdInput);

        // Endereco endereco = new Endereco();
        // endereco.setBairro("Centro");
        // endereco.setCep("78690000");
        // endereco.setCidade(cidade);
        // endereco.setComplemento("Ap 1");
        // endereco.setLogradouro("Rua das Flores");
        // endereco.setNumero("15");
		
		// RestauranteInput restauranteInput = new RestauranteInput();
		// restauranteInput.setNome("Vietnamita");
        // restauranteInput.setAberto(true);
        // restauranteInput.setAtivo(false);
        // restauranteInput.setCozinhaIdInput(cozinhaIdInput);
        // restauranteInput.setTaxaFrete(BigDecimal.valueOf(10L));
        // restauranteInput.setEndereco(endereco);

		// restauranteInputs.add(restauranteInput);
        
        CozinhaIdInput cozinhaIdInput1 = new CozinhaIdInput();        
        cozinhaIdInput1.setNome(faker.food().ingredient());
        cozinhaIdInput1 = cadastroCozinhaService.salvar(cozinhaIdInput1);
        
        RestauranteInput restauranteInput1 = new RestauranteInput();        
        RestauranteInput restauranteInput2 = new RestauranteInput();
        RestauranteInput restauranteInput3 = new RestauranteInput();
        List<RestauranteInput> restauranteInputs = Arrays.asList(restauranteInput1, restauranteInput2, restauranteInput3);

        for (RestauranteInput restauranteInput : restauranteInputs) {
            restauranteInput.setNome(faker.company().name());
            restauranteInput.setTaxaFrete(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)));
            restauranteInput.setCozinhaIdInput(cozinhaIdInput1);    
            restauranteInput = cadastroRestauranteInputService.salvar(restauranteInput);            
        }          

		

		//restauranteInputs.forEach(cadastroRestauranteInputService::salvar);
		
		this.quantidadeRestauranteInputs=restauranteInputs.size();
		this.restauranteInputTeste=restauranteInputs.get(1);

	}

    @Test
    void testBuscar() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", restauranteInputTeste.getId())
            .when()
                .get("/{id}")
            .then()
                .body("nome", Matchers.equalTo(restauranteInputTeste.getNome()))
                .statusCode(HttpStatus.OK.value());
        
        
    }

    @Test
    void testBuscar_NaoExistente(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", RESTAURANTEInput_INEXISTENTE)
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
                .body("nome", Matchers.hasItem(restauranteInputTeste.getNome()))
                .body("", Matchers.hasSize(quantidadeRestauranteInputs));
        
    }

    @Test
    void testSalvar() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(ResourceUtils.getContentFromResource("/json/restauranteInput.json"))
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
                .body(ResourceUtils.getContentFromResource("/json/restauranteInputComErro.json"))
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
*/
    
}
