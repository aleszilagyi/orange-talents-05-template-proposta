package com.orangetalents.proposta.servicosExternos.analiseFinanceira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.UsuarioComRestricaoException;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.propostas.StatusAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsultaRestricao {
    @Autowired
    private ConsultaDadosSolicitante consultaDadosSolicitante;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;

    public StatusAnalise consulta(String documento, String nomeCompleto, String idProposta) {
        try {
            String documentoDecodado = encryptEDecrypt.decrypt(documento);
            FormAnaliseFinanceira analiseDeRestricaoRequest = new FormAnaliseFinanceira(documentoDecodado, nomeCompleto, idProposta);

            ResponseEntity<FormAnaliseFinanceira> restricaoResponse = consultaDadosSolicitante.consultaRestricaoSolicitante(analiseDeRestricaoRequest);
            return restricaoResponse.getBody().getResultadoSolicitacao().normalizaStatus();
        } catch (UsuarioComRestricaoException e) {
            HttpStatus status = HttpStatus.valueOf(e.status());
            String body = e.contentUTF8();
            try {
                FormAnaliseFinanceira payload = new ObjectMapper().readValue(body, FormAnaliseFinanceira.class);
                return payload.getResultadoSolicitacao().normalizaStatus();
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                throw new ErroInternoException();
            }
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }
}
