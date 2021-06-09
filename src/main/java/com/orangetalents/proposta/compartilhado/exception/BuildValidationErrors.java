package com.orangetalents.proposta.compartilhado.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class BuildValidationErrors {

    public static ValidationErrorOutputDto build(MessageSource messageSource, List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();

        globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(messageSource, error)));
        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(messageSource, error);
            validationErrors.addFieldError(error.getField(), errorMessage);
        });
        return validationErrors;
    }

    private static String getErrorMessage(MessageSource messageSource, ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
