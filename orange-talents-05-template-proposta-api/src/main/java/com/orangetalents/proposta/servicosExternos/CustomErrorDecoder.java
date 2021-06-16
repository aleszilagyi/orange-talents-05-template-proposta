package com.orangetalents.proposta.servicosExternos;

import com.orangetalents.proposta.config.exception.httpException.OperacaoNaoPodeSerRealizadaException;
import com.orangetalents.proposta.config.exception.UsuarioComRestricaoException;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 422 && response.request().url().contains(analiseFinanceiraUrl)) {
            try {
                logger.warn("Retorno da API: " + response.body().toString());
                return new UsuarioComRestricaoException(response.status(), s, response.request(), response.body());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ErroInternoException();
            }
        }
        if (response.status() == 422 && response.request().url().contains(cartaoUrl) && response.request().url().contains("/bloqueios")) {
            logger.warn("Retorno da API: " + response.body().toString());
            return new OperacaoNaoPodeSerRealizadaException("Número de cartão já está bloqueado");
        }
        if (response.status() == 500 && response.request().url().contains(cartaoUrl) && response.request().url().contains("/bloqueios")) {
            logger.warn("Retorno da API: " + response.status());
            return new ErroInternoException();
        }
        if (response.status() == 404 && response.request().url().contains(cartaoUrl + "/api/cartoes/")) {
            try {
                logger.warn("Retorno da API: " + response.status());
                return new RecursoNotFoundException("Cartão não encontrado");
            } catch (FeignException e) {
                e.printStackTrace();
                return e;
            }
        }
        if (response.status() == 422 && response.request().url().contains(cartaoUrl) && response.request().url().contains("/avisos")) {
            logger.warn("Retorno da API: " + response.body().toString());
            return new OperacaoNaoPodeSerRealizadaException("Aviso de viagem já cadastrado para o cartão");
        } else return defaultErrorDecoder.decode(s, response);
    }
}
