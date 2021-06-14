package com.orangetalents.biometria.servicosExternos;

import com.orangetalents.biometria.servicosExternos.cartoes.CartaoNaoExisteException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

@PropertySource("classpath:application.properties")
public class CustomErrorDecoder implements ErrorDecoder {
    @Value("${cartao.host}")
    private String cartaoServiceUrl;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404 && response.request().url().contains(cartaoServiceUrl + "/api/cartoes/")) {
            try {
                return new CartaoNaoExisteException(HttpStatus.valueOf(response.status()), "Cartão não encontrado");
            } catch (FeignException e) {
                e.printStackTrace();
                return e;
            }
        } else return defaultErrorDecoder.decode(s, response);
    }
}
