package com.orangetalents.proposta.servicosExternos.cartoes;

import com.orangetalents.proposta.bloqueios.StatusBloqueio;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BloquearCartao {
    @Autowired
    private ConsultaCartao consultaCartao;
    private final SistemaResponsavel sistemaResponsavel = new SistemaResponsavel(this);

    public StatusBloqueio bloquearCartao(String idCartao) {
        try {
            ResponseEntity<BloqueioResponse> response = consultaCartao.bloquearCartao(idCartao, sistemaResponsavel);
            String resultado = response.getBody().getResultado();
            return StatusBloqueio.normalizar(resultado);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }

}
