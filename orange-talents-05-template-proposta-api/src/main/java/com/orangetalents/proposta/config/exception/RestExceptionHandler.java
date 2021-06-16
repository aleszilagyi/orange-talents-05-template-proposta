package com.orangetalents.proposta.config.exception;

import com.orangetalents.proposta.config.exception.httpException.ValidacoesException;
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

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RetornaTipoDeStatus retornaTipoDeStatus;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleRecursoNotFoundException(ResponseStatusException ex) {
        if (ex.getStatus() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getReason());
        }
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorOutputDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ValidationErrorOutputDto errorDto = new ValidationErrorOutputDto(messageSource, globalErrors, fieldErrors);

        HttpStatus status = retornaTipoDeStatus.statusErroDeValidacao(ex);

        return ResponseEntity.status(status).body(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex) {
        ValidationErrorOutputDto validationErrorOutputDto = new ValidationErrorOutputDto();

        ex.getConstraintViolations().stream()
                .forEach(violation -> {
                    validationErrorOutputDto.addFieldError(violation.getPropertyPath().toString(), violation.getMessage());
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorOutputDto);
    }

    @ExceptionHandler(ValidacoesException.class)
    public ResponseEntity handleValidacoesException(ValidacoesException ex) {
        ValidationErrorOutputDto validationErrorOutputDto = new ValidationErrorOutputDto();

        ex.getConstraintViolations().stream()
                .forEach(violation -> {
                    validationErrorOutputDto.addFieldError(violation.getPropertyPath().toString(), violation.getMessage());
                });
        return ResponseEntity.status(ex.getStatus()).body(validationErrorOutputDto);
    }
}

