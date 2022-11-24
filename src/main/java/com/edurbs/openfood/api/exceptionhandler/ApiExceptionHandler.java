package com.edurbs.openfood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edurbs.openfood.core.validation.ValidacaoException;
import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.exception.EntidadeNaoEncontradaException;
import com.edurbs.openfood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import io.micrometer.core.ipc.http.HttpSender.Response;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente. Se o problema persistir, entre em contato com o administrador do sistema";

    @Autowired
    MessageSource messageSource;

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
    
             problem = createProblemBuilder(status, problemType, detail)
                    .build();         
            
        }
        
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
   
        var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        var detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.",
            ((NoHandlerFoundException) ex).getRequestURL());
        var problem = createProblemBuilder(status, problemType, detail).build();        

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

        //HttpStatus status = HttpStatus.NOT_FOUND;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
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


        if(!(body instanceof Problem)){
            ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
            String detail =MSG_ERRO_GENERICA_USUARIO_FINAL;
            Problem problem = createProblemBuilder(status, problemType, detail).build();

            ex.printStackTrace();
            
            return super.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidationException(ValidacaoException ex, WebRequest request){
        
        return handleExceptionInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {           

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }



    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        
        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                    .map(objectError -> {
                            String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                            String name=objectError.getObjectName();
                            if(objectError instanceof FieldError){
                                name=((FieldError) objectError).getField();
                            }
                            return Problem.Object.builder()
                                    .name(name)
                                    .userMessage(msg)
                                    .build();
                        })
                    .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .objects(problemObjects)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);        
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
    String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title((problemType.getTitle()))
                .detail(detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .offsetDateTime(OffsetDateTime.now().toString());
    }



    
}
