package com.orangetalents.proposta.viagens;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class ViagemDestino {
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotBlank
    private String pais;

    @Deprecated
    public ViagemDestino() {
    }

    public ViagemDestino(String cidade, String estado, String pais) {
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }
}
