package com.orangetalents.proposta.servicosExternos.analiseFinanceira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangetalents.proposta.compartilhado.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.geraPropostas.Proposta;
import com.orangetalents.proposta.geraPropostas.StatusAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsultaRestricao {
    @Autowired
    private ConsultaDadosSolicitante consultaDadosSolicitante;

    public StatusAnalise consulta(Proposta proposta) throws JsonProcessingException {
        FormAnaliseFinanceira analiseDeRestricaoRequest = new FormAnaliseFinanceira(proposta);

        try {
            ResponseEntity<FormAnaliseFinanceira> restricaoResponse = consultaDadosSolicitante.consultaRestricaoSolicitante(analiseDeRestricaoRequest);
            return restricaoResponse.getBody().getResultadoSolicitacao().normalizaStatus();
        } catch (UsuarioComRestricaoException e) {
            HttpStatus status = HttpStatus.valueOf(e.status());
            String body = e.contentUTF8();
            FormAnaliseFinanceira payload = new ObjectMapper().readValue(body, FormAnaliseFinanceira.class);
            return payload.getResultadoSolicitacao().normalizaStatus();
        } catch (FeignException e) {
            throw new ErroInternoException();
        }
    }
}
