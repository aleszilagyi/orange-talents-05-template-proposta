package com.orangetalents.biometria.compartilhado.exception;

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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RetornaTipoDeStatus retornaTipoDeStatus;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleRecursoNotFoundException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getReason());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Erro de formatação da requisição";

        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();
        validationErrors.addError(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ValidationErrorOutputDto errorDto = new ValidationErrorOutputDto(messageSource, globalErrors, fieldErrors);

        HttpStatus status = retornaTipoDeStatus.statusErroDeValidacao(ex);

        return ResponseEntity.status(status).body(errorDto);
    }
}

