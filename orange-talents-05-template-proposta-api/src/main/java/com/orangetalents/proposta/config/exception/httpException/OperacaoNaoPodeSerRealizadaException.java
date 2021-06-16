package com.orangetalents.proposta.config.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OperacaoNaoPodeSerRealizadaException extends ResponseStatusException {
    public OperacaoNaoPodeSerRealizadaException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
