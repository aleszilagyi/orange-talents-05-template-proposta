package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class IsUUIDValidator implements ConstraintValidator<IsUUID, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            UUID id = UUID.fromString(s);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RecursoNotFoundException();
        }
        return true;

    }
}
