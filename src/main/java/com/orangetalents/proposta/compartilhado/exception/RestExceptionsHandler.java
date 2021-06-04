package com.orangetalents.proposta.compartilhado.exception;

import com.orangetalents.proposta.compartilhado.validacoes.UniqueValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    //Catcher de errors relacionados ao tipo de formatação do JSON da request
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getCause().getMessage();
        message = message.substring(message.indexOf("problem") + 9, message.indexOf("\n"));

        ValidationErrorOutputDto validationErrors = new ValidationErrorOutputDto();
        validationErrors.addFormatErrorMessages(message);

        status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(validationErrors);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        status = HttpStatus.BAD_REQUEST;

        List<FieldError> uniqueFieldError = fieldErrors.stream().filter(error -> error.getCode()
                .contains(UniqueValue.class.getName())).collect(Collectors.toList());
        if (uniqueFieldError.isEmpty()) status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorOutputDto errorDto = buildValidationErrors(globalErrors, fieldErrors);

        return ResponseEntity.status(status).body(errorDto);
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

