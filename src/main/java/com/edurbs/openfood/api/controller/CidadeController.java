package com.edurbs.openfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.CidadeModelAssembler;
import com.edurbs.openfood.api.model.CidadeApiModel;
import com.edurbs.openfood.api.model.input.CidadeInput;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.model.Cidade;
import com.edurbs.openfood.domain.repository.CidadeRepository;
import com.edurbs.openfood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;




@RestController
@RequestMapping(value="/cidades", produces = "application/json")
public class CidadeController {

    private static final String ID_MAPPING = "/{id}";

    private static final String RESULT_ID = "#result.id";
    private static final String ID_CACHE = "#id";
    private static final String CITY = "city";
    private static final String CITIES = "cities";

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired 
    private CidadeRepository cidadeRepository;

    @GetMapping
    @Cacheable(cacheNames = CITIES, key = "#pageable")
    public Page<CidadeApiModel> listCities( @PageableDefault Pageable pageable) {
        Page<Cidade> cidadesPage = cidadeRepository.findAll(pageable);
        List<Cidade> cidadesList = cidadesPage.getContent();
        List<CidadeApiModel> cidadesApiModel = cidadeModelAssembler.toCollectionApiModel(cidadesList);

        return new PageImpl<>(cidadesApiModel, pageable, cidadesPage.getTotalElements());
    }

    @GetMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(cacheNames = CITY, key = ID_CACHE)
    public CidadeApiModel getCity(@PathVariable Long id) {        
             
        var cidade = cadastroCidadeService.buscar(id);        

        return cidadeModelAssembler.toApiModel(cidade);

        // CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
        // return ResponseEntity.ok()
        //         // .cacheControl(cacheControl)
        //         .body(cidadeApiModel);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)    
    @Caching(put = @CachePut(cacheNames = CITY, key=RESULT_ID),
            evict = @CacheEvict(cacheNames = CITIES, allEntries = true))
    public CidadeApiModel addCity(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeModelAssembler.toDomainModel(cidadeInput);
            Cidade cidadeSalva =cadastroCidadeService.salvar(cidade);            
            return cidadeModelAssembler.toApiModel(cidadeSalva);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    @Caching(put = @CachePut(cacheNames = CITY, key=ID_CACHE),
            evict = @CacheEvict(cacheNames = CITIES, allEntries = true))
    public CidadeApiModel updateCity(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            cadastroCidadeService.buscar(id);        
            Cidade cidade = cidadeModelAssembler.toDomainModel(cidadeInput);
            cidade.setId(id);
            
            Cidade cidadesalva = cadastroCidadeService.salvar(cidade);            

            return cidadeModelAssembler.toApiModel(cidadesalva);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
        
    }

    @DeleteMapping(ID_MAPPING)
    @Caching(evict = {
        @CacheEvict(cacheNames = CITY, key = ID_CACHE),
        @CacheEvict(cacheNames = CITIES, allEntries = true)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id){        
        cadastroCidadeService.remover(id);    
    }
    
    
    
}
