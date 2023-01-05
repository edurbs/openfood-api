package com.edurbs.openfood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.openfood.api.assembler.FotoProdutoAssembler;
import com.edurbs.openfood.api.model.FotoProdutoApiModel;
import com.edurbs.openfood.api.model.input.FotoProdutoInput;
import com.edurbs.openfood.domain.exception.EntityNotFoundException;
import com.edurbs.openfood.domain.model.FotoProduto;
import com.edurbs.openfood.domain.model.Produto;
import com.edurbs.openfood.domain.service.CadastroProdutoService;
import com.edurbs.openfood.domain.service.CatalogoFotoProdutoService;
import com.edurbs.openfood.domain.service.FotoStorageService;
import com.edurbs.openfood.domain.service.FotoStorageService.FotoRecuperada;
import com.edurbs.openfood.infrastructure.service.storage.StorageException;




@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    
    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoApiModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        
        Produto produto = cadastroProdutoService.buscar(produtoId);
       
        FotoProduto fotoProduto = new FotoProduto();
            
        fotoProduto.setId(produtoId);
        fotoProduto.setProduto(produto);
        fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        fotoProduto.setRestauranteId(restauranteId);
        try {
            fotoProduto.setInputStream(fotoProdutoInput.getArquivo().getInputStream());
        } catch (IOException e) {
            throw new StorageException("Erro ao obter dados da foto", e);            
        }

        FotoProduto fotoProdutoSalva = catalogoFotoProdutoService.salvar(fotoProduto);

        return fotoProdutoAssembler.toApiModel(fotoProdutoSalva);        
       
       
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoApiModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
        return fotoProdutoAssembler.toApiModel(fotoProduto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.remover(restauranteId, produtoId);
    }
    

    @GetMapping
    public ResponseEntity<?> buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);
            
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
            verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);

            FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
                if(fotoRecuperada.temUrl()){
                    return ResponseEntity
                            .status(HttpStatus.FOUND)
                            .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                            .build();
                }else{
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
                }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();            
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
        if(!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }
    }
    
    
}
