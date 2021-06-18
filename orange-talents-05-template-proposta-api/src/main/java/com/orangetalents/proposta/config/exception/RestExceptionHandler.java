package com.orangetalents.proposta.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class RestExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RetornaTipoDeStatus retornaTipoDeStatus;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();
        validationErrors.addError(ex.getReason());
        return ResponseEntity.status(ex.getStatus()).body(validationErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Erro de formatação da requisição";
        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();
        validationErrors.addError(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex) {
        return validacoesHandler(ex);
    }

    private ResponseEntity validacoesHandler(ConstraintViolationException ex) {
        ValidationErrorOutputDto validationErrorOutputDto = new ValidationErrorOutputDto();
        AtomicReference<HttpStatus> status = new AtomicReference<>(HttpStatus.BAD_REQUEST);

        ex.getConstraintViolations()
                .forEach(violation -> {
                    HttpStatus statusPayload = retornaTipoDeStatus.verificaPayload(violation);
                    if (statusPayload != HttpStatus.BAD_REQUEST) {
                        status.set(statusPayload);
                        validationErrorOutputDto.addError(violation.getMessage());
                    } else {
                        validationErrorOutputDto.addFieldError(violation.getPropertyPath().toString(), violation.getMessage());
                    }
                });

        return ResponseEntity.status(status.get()).body(validationErrorOutputDto);
    }
}

