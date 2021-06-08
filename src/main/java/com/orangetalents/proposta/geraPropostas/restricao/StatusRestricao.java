package com.orangetalents.proposta.geraPropostas.restricao;

import com.orangetalents.proposta.geraPropostas.StatusAnalise;

public enum StatusRestricao {
    COM_RESTRICAO, SEM_RESTRICAO;

    public StatusAnalise normalizaStatus() {
        if (this == SEM_RESTRICAO) {
            return StatusAnalise.ELEGIVEL;
        }
        return StatusAnalise.NAO_ELEGIVEL;
    }
}
