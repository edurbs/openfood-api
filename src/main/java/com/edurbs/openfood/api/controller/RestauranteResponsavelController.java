package com.edurbs.openfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.UsuarioAssembler;
import com.edurbs.openfood.api.model.UsuarioApiModel;
import com.edurbs.openfood.domain.model.Usuario;
import com.edurbs.openfood.domain.service.CadastroRestauranteService;
import com.edurbs.openfood.domain.service.CadastroUsuarioService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {
    
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioApiModel> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscar(restauranteId);        
        var responsaveis = restaurante.getResponsaveis();
        return usuarioAssembler.toCollectionApiModel(responsaveis);
    }

    @PutMapping(value="/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    
    @DeleteMapping(value="/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
    
}
