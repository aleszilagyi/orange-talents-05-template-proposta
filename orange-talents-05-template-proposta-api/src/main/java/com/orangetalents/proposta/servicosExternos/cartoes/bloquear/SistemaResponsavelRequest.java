package com.orangetalents.proposta.servicosExternos.cartoes.bloquear;

import javax.validation.constraints.NotBlank;

public class SistemaResponsavelRequest {
    @NotBlank
    private String sistemaResponsavel;

    public SistemaResponsavelRequest(Object sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel.getClass().getName();
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
