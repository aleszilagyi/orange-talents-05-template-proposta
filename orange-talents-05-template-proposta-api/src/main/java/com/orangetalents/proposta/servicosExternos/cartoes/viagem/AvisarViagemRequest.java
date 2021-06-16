package com.orangetalents.proposta.servicosExternos.cartoes.viagem;

import java.time.LocalDate;

public class AvisarViagemRequest {
    private String destino;
    private LocalDate validoAte;

    public AvisarViagemRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
