package com.orangetalents.proposta.servicosExternos.analiseFinanceira;

import feign.FeignException;
import feign.Request;
import feign.Response;

import java.io.IOException;

public class UsuarioComRestricaoException extends FeignException {
    public UsuarioComRestricaoException(int status, String message, Request request, Response.Body responseBody) throws IOException {
        super(status, message, request, responseBody.asInputStream().readAllBytes());
    }
}
