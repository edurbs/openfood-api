package com.edurbs.openfood.api.exceptionhandler;

import java.lang.StackWalker.Option;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        
        Throwable rootCause =  ExceptionUtils.getRootCause(ex);
        
        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormat( (InvalidFormatException) rootCause, headers, status, request);
        } else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        } 

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique o erro de sintaxe.";

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
        TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Problem problem = null;
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = null;

        if(ex instanceof MethodArgumentTypeMismatchException){

            
            detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.", 
                    ((MethodArgumentTypeMismatchException) ex).getName(),
                    ex.getValue(),
                    Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName));
    
             problem = createProblemBuilder(status, problemType, detail).build();         
            
        }
        
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PROPRIEDADE_INVALIDA;

        String detail = String.format("Propriedade '%s' inválida.", 
                ex.getPropertyName());
        Problem problem = createProblemBuilder(status, problemType, detail).build();
                
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe o valor compatível com o tipo %s.",
                 ex.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining(".")), 
                 ex.getValue(), 
                 ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada (EntidadeNaoEncontradaException ex, WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
            String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title((problemType.getTitle()))
                .detail(detail);
    }

    
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
        
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_GENERICO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        if(body == null){
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if(body instanceof String){
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    
}