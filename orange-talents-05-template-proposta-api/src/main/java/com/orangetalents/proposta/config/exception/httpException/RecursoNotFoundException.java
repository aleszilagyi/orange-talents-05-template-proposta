package com.orangetalents.proposta.config.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecursoNotFoundException extends ResponseStatusException {
    public RecursoNotFoundException() {
        super(HttpStatus.NOT_FOUND, "O recurso que você está tentando acessar não existe");
    }

    public RecursoNotFoundException(String s) {
        super(HttpStatus.NOT_FOUND, s);
    }
}
