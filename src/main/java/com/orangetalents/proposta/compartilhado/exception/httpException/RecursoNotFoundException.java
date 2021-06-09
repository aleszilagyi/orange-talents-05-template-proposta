package com.orangetalents.proposta.compartilhado.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RecursoNotFoundException extends HttpClientErrorException {
    public RecursoNotFoundException() {
        super(HttpStatus.NOT_FOUND, "O recurso que você está tentando acessar não existe");
    }
}
