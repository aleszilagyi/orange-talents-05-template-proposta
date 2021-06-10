package com.orangetalents.biometria.servicosExternos;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@PropertySource("classpath:application.properties")
public class CustomErrorDecoder implements ErrorDecoder {
    @Value("${cartao.host}")
    private String cartaoServiceUrl;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404 && response.request().url().contains(cartaoServiceUrl)) {
            try {
                return new CartaoNaoExisteException(HttpStatus.valueOf(response.status()), "Cartão não encontrado");
            } catch (FeignException e) {
                return e;
            }
        } else return defaultErrorDecoder.decode(s, response);
    }
}
