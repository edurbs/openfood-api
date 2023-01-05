package com.edurbs.openfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.CityApiModel;
import com.edurbs.openfood.api.model.input.CityInput;
import com.edurbs.openfood.domain.model.City;
import com.edurbs.openfood.domain.model.Estado;

@Component
public class CityModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CityApiModel toApiModel(City cidade){
        return modelMapper.map(cidade, CityApiModel.class);
    }

    public List<CityApiModel> toCollectionApiModel(List<City> cidades){
        return cidades.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public City toDomainModel(CityInput cidadeInput){
        return modelMapper.map(cidadeInput, City.class);
    }

    public void copyToDomainModel(CityInput cidadeInput, City cidade){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of 
        // Estado was altered from 1 to 2
        cidade.setEstado(new Estado());
        
        modelMapper.map(cidadeInput, cidade);
        
    }
}
