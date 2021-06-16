package com.orangetalents.proposta.viagens;

import javax.validation.constraints.NotBlank;

public class DestinoDto {
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotBlank
    private String pais;

    public DestinoDto(String cidade, String estado, String pais) {
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public DestinoDto(ViagemDestino viagemDestino) {
        this.cidade = viagemDestino.getCidade();
        this.estado = viagemDestino.getEstado();
        this.pais = viagemDestino.getPais();
    }

    public ViagemDestino converter() {
        return new ViagemDestino(cidade, estado, pais);
    }

    @Override
    public String toString() {
        return "{" +
                "cidade=" + cidade +
                ", estado=" + estado +
                ", pais=" + pais +
                '}';
    }
}
