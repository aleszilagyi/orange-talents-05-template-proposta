package com.orangetalents.biometria.servicosExternos.cartoes;

import feign.Request;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartaoNaoExisteException extends ResponseStatusException {
    public CartaoNaoExisteException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
