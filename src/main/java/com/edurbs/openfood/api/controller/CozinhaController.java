package com.edurbs.openfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.model.CozinhaXmlWrapper;
import com.edurbs.openfood.domain.repository.CozinhaRepository;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {

        return Optional.ofNullable(cozinhaRepository.buscar(cozinhaId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        // Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        // if(cozinha != null)){
        // return ResponseEntity.ok(cozinha.get());
        // }
        // return ResponseEntity.notFound().build();
        //

    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhaXmlWrapper listarXml() {
        return new CozinhaXmlWrapper(cozinhaRepository.listar());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        return Optional.ofNullable(cozinhaRepository.buscar(id))
                .map(cozinhaAtual -> {
                    BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
                    Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual);
                    return ResponseEntity.ok(cozinhaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable Long id){
        /*return Optional.ofNullable(cozinhaRepository.buscar(id))
                .map(cozinhaAtual -> {
                    try {
                        cozinhaRepository.remover(id);
                        return ResponseEntity.noContent().build();    
                    } catch (DataIntegrityViolationException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();                        
                    }
                }).orElseGet(() -> ResponseEntity.notFound().build());*/
        try {
            cadastroCozinhaService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
    }

}
