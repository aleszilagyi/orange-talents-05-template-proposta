package com.orangetalents.proposta.bloqueios;

import java.util.Locale;

public enum StatusBloqueio {
    DESBLOQUEADO, BLOQUEADO;

    public static StatusBloqueio normalizar(String resultado) {
        if (resultado.toUpperCase(Locale.ROOT).equals("BLOQUEADO")) {
            return StatusBloqueio.BLOQUEADO;
        }
        return StatusBloqueio.DESBLOQUEADO;
    }
}
