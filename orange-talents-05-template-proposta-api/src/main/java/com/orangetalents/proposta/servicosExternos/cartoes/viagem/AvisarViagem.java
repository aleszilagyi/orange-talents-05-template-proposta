package com.orangetalents.proposta.servicosExternos.cartoes.viagem;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AvisarViagem {
    @Autowired
    private ConsultaCartao consultaCartao;
    private final Logger logger = LoggerFactory.getLogger(AvisarViagem.class);

    public void avisar(String idCartaoEncrypted, String destino, LocalDate validoAte, EncryptEDecrypt encryptEDecrypt) {
        try {
            String idCartao = encryptEDecrypt.decrypt(idCartaoEncrypted);
            AvisarViagemRequest request = new AvisarViagemRequest(destino, validoAte);
            ResponseEntity<AvisoViagemResponse> response = consultaCartao.avisarViagem(idCartao, request);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }
}
