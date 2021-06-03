package com.orangetalents.proposta.compartilhado.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CommonExceptionsHandler {
    @Autowired
    private MessageSource messageSource;

    //Catcher de errors relacionados ao tipo de formatação do JSON da request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValueInstantiationException.class)
    public ValidationErrorOutputDto handleJsonFormatNotValid(HttpMessageNotReadableException exception) {
        String message = exception.getCause().getMessage();
        message = message.substring(message.indexOf("problem") + 9, message.indexOf("\n"));

        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();

        validationErrors.addFormatErrorMessages(message);

        return validationErrors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorOutputDto handleValidationError(MethodArgumentNotValidException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationErrors(globalErrors, fieldErrors);
    }

    private ValidationErrorOutputDto buildValidationErrors(List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();

        globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));
        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(error);
            validationErrors.addFieldError(error.getField(), errorMessage);
        });
        return validationErrors;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}

