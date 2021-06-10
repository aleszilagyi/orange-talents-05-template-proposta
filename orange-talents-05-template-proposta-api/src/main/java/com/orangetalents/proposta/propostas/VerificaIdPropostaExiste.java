package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.compartilhado.exception.httpException.RecursoNotFoundException;

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