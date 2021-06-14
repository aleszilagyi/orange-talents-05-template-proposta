package com.orangetalents.proposta.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartaoJaBloqueadoException extends ResponseStatusException {
    public CartaoJaBloqueadoException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
