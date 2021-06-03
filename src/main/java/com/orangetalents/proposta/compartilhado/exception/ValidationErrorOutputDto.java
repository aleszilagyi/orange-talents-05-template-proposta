package com.orangetalents.proposta.compartilhado.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorOutputDto {
    private List<String> formatErrorMessages = new ArrayList<>();
    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorOutputDto> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldError);
    }

    public void addFormatErrorMessages(String message) {
        formatErrorMessages.add(message);
    }

    public List<String> getFormatErrorMessages() {
        return formatErrorMessages;
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutputDto> getFieldErrors() {
        return fieldErrors;
    }
}
