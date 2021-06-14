package com.orangetalents.proposta.servicosExternos.cartoes;

import com.orangetalents.proposta.bloqueios.FormBloquearCartao;
import com.orangetalents.proposta.bloqueios.StatusBloqueio;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.config.validacoes.outros.CartaoNemExiste;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class BloquearCartao {
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private CartaoNemExiste cartaoNemExiste;
    private final SistemaResponsavel sistemaResponsavel = new SistemaResponsavel(this);

    public FormBloquearCartao bloquear(String userIp, String userAgent, String idCartao) {
        try {
            cartaoNemExiste.validate(idCartao);
            ResponseEntity<BloqueioResponse> response = consultaCartao.bloquearCartao(idCartao, sistemaResponsavel);
            String resultado = response.getBody().getResultado();
            StatusBloqueio statusBloqueio = StatusBloqueio.normalizar(resultado);

            return new FormBloquearCartao(idCartao, userIp, userAgent, statusBloqueio);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }

}