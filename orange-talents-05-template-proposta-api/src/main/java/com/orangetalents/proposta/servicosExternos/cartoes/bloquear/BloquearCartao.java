package com.orangetalents.proposta.servicosExternos.cartoes.bloquear;

import com.orangetalents.proposta.bloqueios.StatusBloqueio;
import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BloquearCartao {
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    private final Logger logger = LoggerFactory.getLogger(BloquearCartao.class);

    private final SistemaResponsavelRequest sistemaResponsavelRequest = new SistemaResponsavelRequest(this);

    public StatusBloqueio bloquear(String userIp, String userId, String userAgent, String idCartaoEncrypted) {
        try {
            String idCartao = encryptEDecrypt.decrypt(idCartaoEncrypted);
            ResponseEntity<BloqueioResponse> response = consultaCartao.bloquearCartao(idCartao, sistemaResponsavelRequest);
            String resultado = response.getBody().getResultado();
            return StatusBloqueio.normalizar(resultado);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }

}
