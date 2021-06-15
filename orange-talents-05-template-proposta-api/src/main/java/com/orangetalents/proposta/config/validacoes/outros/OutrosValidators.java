package com.orangetalents.proposta.config.validacoes.outros;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutrosValidators {
    @Autowired
    private ConsultaCartao consultaCartao;

    public void cartaoExisteApiExterna(String idCartao) {
        try {
            String numCartao = consultaCartao.consultaCartao(idCartao).getBody().getId();
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }

    public UUID verificaUUID(String idProposta) {
        try {
            return UUID.fromString(idProposta);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RecursoNotFoundException();
        }
    }
}
