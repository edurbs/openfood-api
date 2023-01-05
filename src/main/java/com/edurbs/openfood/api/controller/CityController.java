package com.edurbs.openfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
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

import com.edurbs.openfood.api.assembler.CityModelAssembler;
import com.edurbs.openfood.api.model.CityApiModel;
import com.edurbs.openfood.api.model.input.CityInput;
import com.edurbs.openfood.api.util.ResourceUriHelper;
import com.edurbs.openfood.domain.exception.BusinessException;
import com.edurbs.openfood.domain.exception.EntityNotFoundException;
import com.edurbs.openfood.domain.model.City;
import com.edurbs.openfood.domain.repository.CityRepository;
import com.edurbs.openfood.domain.service.RegistryCityService;




@RestController
@RequestMapping(value="/cities", produces = "application/json")
public class CityController {

    private static final String ID_MAPPING = "/{id}";

    private static final String RESULT_ID = "#result.id";
    private static final String ID_CACHE = "#id";
    private static final String CITY = "city";
    private static final String CITIES = "cities";

    @Autowired
    private RegistryCityService registryCityService;

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired 
    private CityRepository cityRepository;

    @GetMapping
    //@Cacheable(cacheNames = CITIES, key = "#pageable")
    public Page<CityApiModel> listCities( @PageableDefault Pageable pageable) {
        Page<City> citiesPage = cityRepository.findAll(pageable);
        List<City> citiesList = citiesPage.getContent();
        List<CityApiModel> citiesApiModel = cityModelAssembler.toCollectionApiModel(citiesList);

        return new PageImpl<>(citiesApiModel, pageable, citiesPage.getTotalElements());
    }

    @GetMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    //@Cacheable(cacheNames = CITY, key = ID_CACHE)
    public CityApiModel getCity(@PathVariable Long id) {        
        var city = registryCityService.find(id);        

        var cityApiModel = cityModelAssembler.toApiModel(city);
        
        cityApiModel.add(Link.of("http://localhost:8080/cities/"+id));
        cityApiModel.add(Link.of("http://localhost:8080/cities", "cities"));

        cityApiModel.getEstado().add(Link.of("http://localhost:8080/estados/"+cityApiModel.getEstado().getId()));
        return cityApiModel;
     
     }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)    
    //@Caching(put = @CachePut(cacheNames = CITY, key=RESULT_ID),
    //        evict = @CacheEvict(cacheNames = CITIES, allEntries = true))
    public CityApiModel addCity(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityModelAssembler.toDomainModel(cityInput);
            City savedCity =registryCityService.salvar(city);            
            CityApiModel cityApiModel = cityModelAssembler.toApiModel(savedCity);

            ResourceUriHelper.addUriResponseHeader(cityApiModel.getId());
                
            return cityApiModel;

        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    //@Caching(put = @CachePut(cacheNames = CITY, key=ID_CACHE),
    //        evict = @CacheEvict(cacheNames = CITIES, allEntries = true))
    public CityApiModel updateCity(@PathVariable Long id, @RequestBody @Valid CityInput cityInput) {
        try {
            registryCityService.find(id);        
            City city = cityModelAssembler.toDomainModel(cityInput);
            city.setId(id);
            
            City citySaved = registryCityService.salvar(city);            

            return cityModelAssembler.toApiModel(citySaved);

        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
        
    }

    @DeleteMapping(ID_MAPPING)
    // @Caching(evict = {
    //     @CacheEvict(cacheNames = CITY, key = ID_CACHE),
    //     @CacheEvict(cacheNames = CITIES, allEntries = true)
    // })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id){        
        registryCityService.remover(id);    
    }
    
    
    
}
