package com.orangetalents.biometria.compartilhado.validacoes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.bind.DatatypeConverter;

public class IsBase64Validator implements ConstraintValidator<IsBase64, String> {
    @Override
    public boolean isValid(String byteBase64, ConstraintValidatorContext constraintValidatorContext) {
        try {
            DatatypeConverter.parseBase64Binary(byteBase64);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
