package com.orangetalents.proposta.externos;

import feign.FeignException;
import feign.Request;

public class UsuarioComRestricaoException extends FeignException {
    public UsuarioComRestricaoException(int status, String message, Request request, byte[] responseBody) {
        super(status, message, request, responseBody);
    }
}
