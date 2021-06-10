package com.orangetalents.biometria.servicosExternos.cartoes;

import java.time.LocalDateTime;

public class CartaoAvisosResponse {
    private LocalDateTime validoAte;
    private String destino;

    public CartaoAvisosResponse(LocalDateTime validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDateTime getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
