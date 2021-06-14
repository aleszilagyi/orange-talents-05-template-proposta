package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;

import java.util.UUID;

public class VerificaIdPropostaExiste {
    public static UUID verifica(String idProposta) {
        try {
            return UUID.fromString(idProposta);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RecursoNotFoundException();
        }
    }
}