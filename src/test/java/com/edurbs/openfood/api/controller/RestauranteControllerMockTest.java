package com.edurbs.openfood.api.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;
import com.github.javafaker.Faker;


@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestauranteControllerMockTest {
    
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CadastroRestauranteService cadastroRestauranteService;

    private Faker faker = new Faker(new Locale("pt_BR"));

    private final long RESTAURANTE_INEXISTENTE = 10000L;
    
    private Restaurante restaurante1 = new Restaurante();
    private Restaurante restaurante2 = new Restaurante();
    private Restaurante restaurante3 = new Restaurante();
    private Cozinha cozinha1 = new Cozinha();
    private List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2, restaurante3);
    private int quantidadeRestaurantes;

    
    @BeforeEach
	private void prepararDados() {

        cozinha1.setId(faker.number().randomNumber());
        cozinha1.setNome(faker.food().ingredient());

        restaurantes.forEach(restaurante -> {
            restaurante.setId(faker.number().randomNumber());
            restaurante.setNome(faker.company().name());
            restaurante.setTaxaFrete(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)));
            restaurante.setCozinha(cozinha1);    
        });

		quantidadeRestaurantes=restaurantes.size();	

	}

    @Test
    void shouldReturnListRestaurante_whenGetListRestaurante() throws Exception{
        when(cadastroRestauranteService.listar()).thenReturn(restaurantes);

        mockMvc.perform(
            MockMvcRequestBuilders
                    .get("/restaurantes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            MockMvcResultMatchers
                .status().isOk(),
            MockMvcResultMatchers
                .jsonPath(
                    "$.size()", 
                    CoreMatchers.is(quantidadeRestaurantes)
                )
        ).andDo(MockMvcResultHandlers.print());
        
    }
}
