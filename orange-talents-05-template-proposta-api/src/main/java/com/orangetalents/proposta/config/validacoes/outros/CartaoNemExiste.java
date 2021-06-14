package com.orangetalents.proposta.config.validacoes.outros;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartaoNemExiste {

    @Autowired
    private ConsultaCartao consultaCartao;

    public void validate(String idCartao) {
        try {
            String numCartao = consultaCartao.consultaCartao(idCartao).getBody().getId();
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }
}
