package com.orangetalents.proposta.config.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErroInternoException extends ResponseStatusException {
    public ErroInternoException() {
        super(HttpStatus.BAD_REQUEST, "Desculpe, erro nosso. Tente novamente.");
    }
}
