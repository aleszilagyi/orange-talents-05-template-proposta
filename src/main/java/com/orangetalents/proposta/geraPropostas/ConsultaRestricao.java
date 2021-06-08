package com.orangetalents.proposta.geraPropostas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangetalents.proposta.externos.ConsultaDadosSolicitante;
import com.orangetalents.proposta.externos.FormAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsultaRestricao {
    @Autowired
    private ConsultaDadosSolicitante consultaDadosSolicitante;

    public void consulta(Proposta proposta) throws JsonProcessingException {
        FormAnalise analiseDeRestricaoRequest = new FormAnalise(proposta);

        try {
            ResponseEntity<FormAnalise> restricaoResponse = consultaDadosSolicitante.consultaRestricaoSolicitante(analiseDeRestricaoRequest);
            proposta.atualizaStatusAnalise(restricaoResponse.getBody().getResultadoSolicitacao().normalizaStatus());
        } catch (FeignException e) {
            HttpStatus status = HttpStatus.valueOf(e.status());
            String body = e.contentUTF8();
            FormAnalise payload = new ObjectMapper().readValue(body, FormAnalise.class);
            proposta.atualizaStatusAnalise(payload.getResultadoSolicitacao().normalizaStatus());
        }
    }
}
