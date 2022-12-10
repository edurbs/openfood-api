package com.edurbs.openfood.api.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.edurbs.openfood.domain.exception.CidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.service.CadastroCidadeService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@WebMvcTest(controllers = CidadeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CidadeControllerMockTest {

    private static final Long CIDADE_NAO_EXISTENTE = 10000L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastroCidadeService cadastroCidadeService;

    // @Autowired
    // private ObjectMapper objectMapper;


    private Cidade cidade1;
    private Cidade cidade2;
    private Cidade cidade3;
    private List<Cidade> cidades = new ArrayList<>();
    private Estado estado;



    @BeforeEach
    public void init(){

        //Faker faker = new Faker(new Locale("pt_BR"));
        //new Locale.Builder().setLanguage("pt").setVariant("BR").build();

        // estado = Estado.builder().nome(faker.address().state()).id(1L).build();
        // cidade1 = Cidade.builder().nome(faker.address().cityName()).estado(estado).id(1L).build();
        // cidade2 = Cidade.builder().nome(faker.address().cityName()).estado(estado).id(2L).build();
        // cidade3 = Cidade.builder().nome(faker.address().cityName()).estado(estado).id(3L).build();

        PodamFactory podamFactory = new PodamFactoryImpl();

        
        // estado = podamFactory.manufacturePojo(Estado.class);
        cidade1 = podamFactory.manufacturePojo(Cidade.class);
        cidade2 = podamFactory.manufacturePojo(Cidade.class);
        cidade3 = podamFactory.manufacturePojo(Cidade.class);
        
        
        cidades.add(cidade1);
        cidades.add(cidade2);
        cidades.add(cidade3);



    }

    @Test
    public void shouldReturnCidade1_whenGetCidade1() throws Exception {
        Long cidadeId = cidade1.getId();
        when(cadastroCidadeService.find(cidadeId)).thenReturn(cidade1);        
        
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cidades/"+cidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                //.content(objectMapper.writeValueAsString(cidade))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.nome", Matchers.is(cidade1.getNome())),
                        MockMvcResultMatchers.jsonPath("$.id", Matchers.is(cidade1.getId()), Long.class),
                        MockMvcResultMatchers.jsonPath("$.estado.id", Matchers.is(cidade1.getEstado().getId()), Long.class),
                        MockMvcResultMatchers.jsonPath("$.estado.nome", Matchers.is(cidade1.getEstado().getNome()))
                )
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void shouldReturnList_whenGetAllCidade() throws Exception {
        when(cadastroCidadeService.listar()).thenReturn(cidades);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )    
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(cidades.size()))
                )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shoudlDelete_whenDeleteCidade1() throws Exception {
        doNothing().when(cadastroCidadeService).remover(cidade1.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/cidades/{id}", cidade1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)                               
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNoContent()                        
                );

    }

    @Test
    void shouldReturn404_whenDeleteCidadeNonExistent() throws Exception {
        doThrow(new CidadeNaoEncontradaException(CIDADE_NAO_EXISTENTE)).when(cadastroCidadeService).remover(CIDADE_NAO_EXISTENTE);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/cidades/{id}", CIDADE_NAO_EXISTENTE)   
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                    MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    void BDDway_shouldReturn404_whenDeleteCidadeNonExistent() throws Exception {
        BDDMockito
            .willThrow(new CidadeNaoEncontradaException(CIDADE_NAO_EXISTENTE))
            .given(cadastroCidadeService).remover(CIDADE_NAO_EXISTENTE);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/cidades/{id}", CIDADE_NAO_EXISTENTE)   
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                    MockMvcResultMatchers.status().isNotFound()
                );
    }

}
