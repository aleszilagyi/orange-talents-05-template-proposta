package com.orangetalents.proposta.servicosExternos;

import com.orangetalents.proposta.config.exception.CartaoJaBloqueadoException;
import com.orangetalents.proposta.config.exception.CartaoNaoExisteException;
import com.orangetalents.proposta.config.exception.UsuarioComRestricaoException;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@PropertySource("classpath:application.properties")
public class CustomErrorDecoder implements ErrorDecoder {
    @Value("${restricao.host}")
    private String analiseFinanceiraUrl;
    @Value("${cartao.host}")
    private String cartaoUrl;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 422 && response.request().url().contains(analiseFinanceiraUrl)) {
            try {
                return new UsuarioComRestricaoException(response.status(), s, response.request(), response.body());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ErroInternoException();
            }
        }
        if (response.status() == 422 && response.request().url().contains(cartaoUrl) && response.request().url().contains("/bloqueios")) {
            return new CartaoJaBloqueadoException(HttpStatus.valueOf(response.status()), "Número de cartão já está bloqueado");
        }
        if (response.status() == 500 && response.request().url().contains(cartaoUrl) && response.request().url().contains("/bloqueios")) {
            return new ErroInternoException();
        }
        if (response.status() == 404 && response.request().url().contains(cartaoUrl + "/api/cartoes/")) {
            try {
                return new CartaoNaoExisteException(HttpStatus.valueOf(response.status()), "Cartão não encontrado");
            } catch (FeignException e) {
                e.printStackTrace();
                return e;
            }
        } else return defaultErrorDecoder.decode(s, response);
    }
}
