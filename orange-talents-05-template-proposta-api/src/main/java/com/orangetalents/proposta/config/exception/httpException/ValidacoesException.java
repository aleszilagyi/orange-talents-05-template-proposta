package com.orangetalents.proposta.config.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidacoesException extends ResponseStatusException {
    private Set<? extends ConstraintViolation<?>> constraintViolations;

    public ValidacoesException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(HttpStatus.BAD_REQUEST);
        this.constraintViolations = constraintViolations;
    }

    public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }
}
