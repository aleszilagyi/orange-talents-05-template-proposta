package com.orangetalents.proposta.config.exception;

import com.orangetalents.proposta.config.validacoes.UniqueRawValue;
import com.orangetalents.proposta.config.validacoes.payload.PayloadsApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetornaTipoDeStatus {

    public HttpStatus verificaPayload(ConstraintViolation<?> violation) {
        List<HttpStatus> statusList = violation.getConstraintDescriptor().getPayload().stream().map(payload -> {
            if (!payload.getName().equals("")) {
                try {
                    return ((PayloadsApi) payload.getDeclaredConstructor().newInstance()).getStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return HttpStatus.BAD_REQUEST;
        }).collect(Collectors.toList());

        if (statusList.contains(HttpStatus.FORBIDDEN)) return HttpStatus.FORBIDDEN;
        if (statusList.contains(HttpStatus.UNPROCESSABLE_ENTITY)) return HttpStatus.UNPROCESSABLE_ENTITY;
        return HttpStatus.BAD_REQUEST;
    }

}
