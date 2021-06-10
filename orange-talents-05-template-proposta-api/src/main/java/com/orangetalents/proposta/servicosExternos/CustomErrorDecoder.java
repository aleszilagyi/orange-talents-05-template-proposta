package com.orangetalents.proposta.servicosExternos;

import com.orangetalents.proposta.servicosExternos.analiseFinanceira.UsuarioComRestricaoException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@PropertySource("classpath:application.properties")
public class CustomErrorDecoder implements ErrorDecoder {
    @Value("${restricao.host}")
    private String analiseFinanceiraUrl;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 422 && response.request().url().contains(analiseFinanceiraUrl)) {
            try {
                return new UsuarioComRestricaoException(response.status(), s, response.request(), response.body());
            } catch (IOException e) {
                return e;
            }
        } else return defaultErrorDecoder.decode(s, response);
    }
}
