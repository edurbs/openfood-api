package com.edurbs.openfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edurbs.openfood.api.model.EnderecoApiModel;
import com.edurbs.openfood.api.model.input.ItemPedidoInput;
import com.edurbs.openfood.domain.model.Endereco;
import com.edurbs.openfood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        // modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
        //         .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setTaxaFrete); 

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        modelMapper.createTypeMap(Endereco.class, EnderecoApiModel.class)
                .<String>addMapping(
                        enderecoDomainModel -> enderecoDomainModel.getCidade().getEstado().getNome(), 
                        (enderecoApiModel, valor) -> enderecoApiModel.getCidade().setEstado(valor) );

        return modelMapper;
        
    }
}

