package com.edurbs.openfood.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.PedidoAssembler;
import com.edurbs.openfood.api.assembler.PedidoResumoAssembler;
import com.edurbs.openfood.api.model.PedidoApiModel;
import com.edurbs.openfood.api.model.PedidoResumoApiModel;
import com.edurbs.openfood.api.model.input.PedidoInput;
import com.edurbs.openfood.core.data.PageableTranslator;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.edurbs.openfood.domain.filter.PedidoFilter;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.repository.PedidoRepository;
import com.edurbs.openfood.domain.service.CadastroPedidoService;
import com.edurbs.openfood.infrastructure.repository.specifications.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.ImmutableMap;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private PedidoAssembler pedidoAssembler;

    @Autowired
    private PedidoResumoAssembler pedidoResumoAssembler;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public Page<PedidoResumoApiModel> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size=10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
        
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
        List<Pedido> pedidosList = pedidosPage.getContent();

        List<PedidoResumoApiModel> pedidosResumidosApiModel = pedidoResumoAssembler.toCollectionApiModel(pedidosList);

        return new PageImpl<>(pedidosResumidosApiModel, pageable, pedidosPage.getTotalElements());
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
            "codigo" , "codigo",
            "restaurante.nome", "restaurante.nome",
            "nomeCliente", "cliente.nome",
            "valorTotal", "valorTotal"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
    /*
    @GetMapping()
    public MappingJacksonValue listarFiltrando(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = cadastroPedidoService.listar();
        var pedidosResumoApiModel = pedidoAssembler.toCollectionApiModel(pedidos);

        var wrapperPedidos = new MappingJacksonValue(pedidosResumoApiModel);

        var simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("pedidosFilter", SimpleBeanPropertyFilter.serializeAll());

        if(StringUtils.isNotBlank(campos)){
            simpleFilterProvider.addFilter("pedidosFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        wrapperPedidos.setFilters(simpleFilterProvider);

        return wrapperPedidos;
    }*/
    

    @GetMapping(value="/{codigoPedido}")
    public PedidoApiModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = cadastroPedidoService.buscar(codigoPedido);
        return pedidoAssembler.toApiModel(pedido);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoApiModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {

        
        Usuario clienteFixo = new Usuario();
        clienteFixo.setId(1L);
        clienteFixo.setNome("Maria das Dores");
        clienteFixo.setEmail("maria@gmail.com");
       
        
        var pedido = pedidoAssembler.toDomainModel(pedidoInput);
        pedido.setCliente(clienteFixo);

        try {
            var pedidoSalvo = cadastroPedidoService.salvar(pedido);
            return pedidoAssembler.toApiModel(pedidoSalvo);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());            
        }
        
        
        
    }
    
    
    
    
}
