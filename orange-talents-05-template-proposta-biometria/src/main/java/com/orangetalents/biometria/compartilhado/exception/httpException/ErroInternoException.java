package com.orangetalents.biometria.compartilhado.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErroInternoException extends ResponseStatusException {
    public ErroInternoException() {
        super(HttpStatus.BAD_REQUEST, "Desculpe, erro nosso. Tente novamente.");
    }
}
