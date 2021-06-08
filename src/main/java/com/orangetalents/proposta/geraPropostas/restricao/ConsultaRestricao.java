package com.orangetalents.proposta.geraPropostas.restricao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangetalents.proposta.externos.ConsultaDadosSolicitante;
import com.orangetalents.proposta.externos.UsuarioComRestricaoException;
import com.orangetalents.proposta.geraPropostas.Proposta;
import com.orangetalents.proposta.geraPropostas.StatusAnalise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsultaRestricao {
    @Autowired
    private ConsultaDadosSolicitante consultaDadosSolicitante;

    public StatusAnalise consulta(Proposta proposta) throws JsonProcessingException {
        FormAnalise analiseDeRestricaoRequest = new FormAnalise(proposta);

        try {
            ResponseEntity<FormAnalise> restricaoResponse = consultaDadosSolicitante.consultaRestricaoSolicitante(analiseDeRestricaoRequest);
            return restricaoResponse.getBody().getResultadoSolicitacao().normalizaStatus();
        } catch (UsuarioComRestricaoException e) {
            HttpStatus status = HttpStatus.valueOf(e.status());
            String body = e.contentUTF8();
            FormAnalise payload = new ObjectMapper().readValue(body, FormAnalise.class);
            return payload.getResultadoSolicitacao().normalizaStatus();
        }
    }
}
