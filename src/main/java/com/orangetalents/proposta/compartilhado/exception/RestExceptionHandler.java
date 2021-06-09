package com.orangetalents.proposta.compartilhado.exception;

import com.orangetalents.proposta.compartilhado.exception.httpException.RecursoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RetornaTipoDeStatus retornaTipoDeStatus;

    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity handleRecursoNotFoundException(RecursoNotFoundException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getStatusText());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "erro de formatação da requisição";

        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();
        validationErrors.addError(message);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(validationErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ValidationErrorOutputDto errorDto = BuildValidationErrors.build(messageSource, globalErrors, fieldErrors);

        HttpStatus status = retornaTipoDeStatus.statusErroDeValidacao(ex);

        return ResponseEntity.status(status).body(errorDto);
    }
}

