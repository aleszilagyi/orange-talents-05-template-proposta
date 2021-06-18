package com.orangetalents.proposta.servicosExternos.cartoes.detalhes;

import java.time.LocalDate;

public class CartaoAvisosResponse {
    private LocalDate validoAte;
    private String destino;

    public CartaoAvisosResponse(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
