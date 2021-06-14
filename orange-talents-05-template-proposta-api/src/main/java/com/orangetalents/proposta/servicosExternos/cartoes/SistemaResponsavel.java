package com.orangetalents.proposta.servicosExternos.cartoes;

import javax.validation.constraints.NotBlank;

public class SistemaResponsavel {
    @NotBlank
    private String sistemaResponsavel;

    public SistemaResponsavel(Object sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel.getClass().getName();
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
