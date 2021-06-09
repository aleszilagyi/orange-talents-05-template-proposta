package com.orangetalents.proposta.geraPropostas;

import com.orangetalents.proposta.compartilhado.exception.httpException.RecursoNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class VerificaIdPropostaExiste {
    public static UUID verifica(String idProposta) {
        try {
            return UUID.fromString(idProposta);
        } catch (IllegalArgumentException e) {
            throw new RecursoNotFoundException();
        }
    }
}