package com.orangetalents.proposta.config.exception.httpException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AcaoProibidaException extends ResponseStatusException {
    public AcaoProibidaException() {
        super(HttpStatus.FORBIDDEN, "Ação não autorizada");
    }
}
