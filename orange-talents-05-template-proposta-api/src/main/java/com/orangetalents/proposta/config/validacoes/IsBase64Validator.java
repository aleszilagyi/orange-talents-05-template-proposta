package com.orangetalents.proposta.config.validacoes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {
    @Override
    public boolean isValid(String stringBase64, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Base64.getDecoder().decode(stringBase64);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
