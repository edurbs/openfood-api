package com.edurbs.openfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.edurbs.openfood.api.assembler.EstadoModelAssembler;
import com.edurbs.openfood.api.model.EstadoApiModel;
import com.edurbs.openfood.api.model.input.EstadoInput;
import com.edurbs.openfood.domain.model.Estado;
import com.edurbs.openfood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @GetMapping
    public List<EstadoApiModel> listar() {
        return estadoModelAssembler.toCollectionApiModel(cadastroEstadoService.listar());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoApiModel buscar(@PathVariable Long id) {
        return estadoModelAssembler.toApiModel(cadastroEstadoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoApiModel adicionar (@RequestBody @Valid EstadoInput estadoInput) {        
        var estado = estadoModelAssembler.toDomainModel(estadoInput);
        var estadoSalvo = cadastroEstadoService.salvar(estado);
        return estadoModelAssembler.toApiModel(estadoSalvo);
        
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoApiModel atualizar (@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstadoService.buscar(id);

        //BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoModelAssembler.copyToDomainModel(estadoInput, estadoAtual);
        Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual);
        
        return estadoModelAssembler.toApiModel(estadoSalvo);
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){       
        cadastroEstadoService.remover(id);  
        
    }
    

    
    
    
}
