package com.orangetalents.proposta.config.exception;

import com.orangetalents.proposta.config.validacoes.UniqueValue;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetornaTipoDeStatus {

    public HttpStatus statusErroDeValidacao(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        if (contemUniqueValueError(fieldErrors)) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return status;
    }

    private boolean contemUniqueValueError(List<FieldError> fieldErrors) {
        List<FieldError> uniqueFieldError = fieldErrors.stream().filter(error -> error.getCode()
                .contains(UniqueValue.class.getSimpleName())).collect(Collectors.toList());
        return !uniqueFieldError.isEmpty();
    }
}
