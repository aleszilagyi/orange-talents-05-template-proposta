package com.orangetalents.proposta.servicosExternos.analiseFinanceira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.propostas.StatusAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsultaRestricao {
    @Autowired
    private ConsultaDadosSolicitante consultaDadosSolicitante;

    public StatusAnalise consulta(String documento, String nome, UUID propostaId) {
        FormAnaliseFinanceira analiseDeRestricaoRequest = new FormAnaliseFinanceira(documento, nome, propostaId.toString());

        try {
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
