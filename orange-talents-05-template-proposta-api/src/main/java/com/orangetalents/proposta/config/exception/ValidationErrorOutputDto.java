package com.orangetalents.proposta.config.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorOutputDto {
    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorOutputDto> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutputDto> getFieldErrors() {
        return fieldErrors;
    }

    public ValidationErrorOutputDto() {
    }

    public ValidationErrorOutputDto(MessageSource messageSource, List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        globalErrors.forEach(error -> addError(getErrorMessage(messageSource, error)));
        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(messageSource, error);
            addFieldError(error.getField(), errorMessage);
        });
    }

    private String getErrorMessage(MessageSource messageSource, ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
