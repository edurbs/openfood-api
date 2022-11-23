package com.edurbs.openfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.api.model.FormaPagamentoApiModel;
import com.edurbs.openfood.api.model.input.FormaPagamentoInput;
import com.edurbs.openfood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoAssembler {
    
    @Autowired
    ModelMapper modelMapper;

    public FormaPagamentoApiModel toApiModel(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento, FormaPagamentoApiModel.class);
    }

    public List<FormaPagamentoApiModel> toCollectionApiModel(Collection<FormaPagamento> formaPagamentos){
        return formaPagamentos.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList());
    }

    public FormaPagamento toDomainModel(FormaPagamentoInput formaPagamentoInput){
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainModel(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento){
        modelMapper.map(formaPagamentoInput, formaPagamento);
        
    }

}
