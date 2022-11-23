package com.edurbs.openfood.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.RestauranteModelAssembler;
import com.edurbs.openfood.api.model.RestauranteApiModel;
import com.edurbs.openfood.api.model.input.RestauranteInput;
import com.edurbs.openfood.domain.exception.CidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.CozinhaNaoEncontradaException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.model.Restaurante;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;



@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    private Restaurante domainModel;

    private Restaurante salvar;


    @GetMapping()
    public List<RestauranteApiModel> listar() {
        return restauranteModelAssembler.toCollectionApiModel(cadastroRestauranteService.listar());      
       
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteApiModel buscar(@PathVariable Long id, HttpServletRequest httpServletRequest) {    
            ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(httpServletRequest);
            try {
                Restaurante restaurante = cadastroRestauranteService.buscar(id);       
                
                return restauranteModelAssembler.toApiModel(restaurante);
                
                
            } catch (TypeMismatchException e) {
                Throwable rootCause =ExceptionUtils.getRootCause(e);
                throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
            }
    }

    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroRestauranteService.remover(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteApiModel salvar(@RequestBody @Valid RestauranteInput restauranteInput) {       
        try {
            var domainModel = restauranteModelAssembler.toDomainModel(restauranteInput);
            var salvar = cadastroRestauranteService.salvar(domainModel);
            return restauranteModelAssembler.toApiModel(salvar);  
            
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
        
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteApiModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            // Restaurante restaurante = restauranteInputDesassembler.toDomainModel(restauranteInput);
            Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);
            
            restauranteModelAssembler.copyToDomainModel(restauranteInput, restauranteAtual);

            // BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "dataCadastro", "formasPagamento", "endereco", "produtos");
        
            return restauranteModelAssembler.toApiModel(cadastroRestauranteService.salvar(restauranteAtual));  
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        cadastroRestauranteService.ativar(id);        
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        cadastroRestauranteService.desativar(id);        
    }

    
    @GetMapping("/ativos-com-frete-gratis")
    public List<RestauranteApiModel> listarAtivosComFreteGratis() {
        return restauranteModelAssembler.toCollectionApiModel(cadastroRestauranteService.listarAtivosComFreteGratis());      
       
    }

    @GetMapping("/primeiro")
    public RestauranteApiModel buscaPrimeiro() {
        return restauranteModelAssembler.toApiModel(cadastroRestauranteService.buscaPrimeiro());
       
    }


    /*@PatchMapping("/{id}")
    public RestauranteModel atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest httpServletRequest){
        Restaurante restauranteAtual = cadastroRestauranteService.buscar(id);

        merge(campos, restauranteAtual, httpServletRequest);

        validate(restauranteAtual, "restauranteInput");

        return atualizar(id, restauranteAtual);

    }

    private void validate(Restaurante restauranteAtual, String objectName) {

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);
        smartValidator.validate(restauranteAtual, bindingResult);
        if(bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest httpServletRequest){

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(httpServletRequest);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
    
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
    
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
               
                field.setAccessible(true); // atributo era privado
    
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
            
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);

        }
        
    }*/





}
