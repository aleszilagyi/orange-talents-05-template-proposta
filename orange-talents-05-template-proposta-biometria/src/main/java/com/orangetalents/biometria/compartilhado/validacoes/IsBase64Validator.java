package com.orangetalents.biometria.compartilhado.validacoes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.bind.DatatypeConverter;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {
    @Override
    public boolean isValid(String stringBase64, ConstraintValidatorContext constraintValidatorContext) {
        try {
            DatatypeConverter.parseBase64Binary(stringBase64);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
