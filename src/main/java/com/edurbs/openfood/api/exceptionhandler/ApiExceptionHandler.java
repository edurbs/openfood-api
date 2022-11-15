package com.edurbs.openfood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> estadoNaoEncontradoException (EntidadeNaoEncontradaException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getProblema(e));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> negocioException(NegocioException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getProblema(e));
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> entidadeEmUsoException(EntidadeEmUsoException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(getProblema(e));
    }

    private Problema getProblema(Exception e) {
        return Problema.builder()
                .mensagem(e.getMessage())
                .dataHora(LocalDateTime.now())
                .build();
    }

    
}
