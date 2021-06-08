package com.orangetalents.proposta.compartilhado.exception;

import com.orangetalents.proposta.compartilhado.validacoes.UniqueValue;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

public class RetornaTipoDeStatus {

    public static HttpStatus statusErroDeValidacao(BindException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        if (isUniqueValueError(fieldErrors)) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return status;
    }

    private static boolean isUniqueValueError(List<FieldError> fieldErrors) {
        List<FieldError> uniqueFieldError = fieldErrors.stream().filter(error -> error.getCode()
                .contains(UniqueValue.class.getName())).collect(Collectors.toList());
        return uniqueFieldError.isEmpty();
    }
}
