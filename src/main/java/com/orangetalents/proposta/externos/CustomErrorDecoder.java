package com.orangetalents.proposta.externos;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 422) {
            try {
                return new UsuarioComRestricaoException(response.status(), s, response.request(), response.body().asInputStream().readAllBytes());
            } catch (IOException e) {
                return e;
            }
        } else return defaultErrorDecoder.decode(s, response);
    }
}
