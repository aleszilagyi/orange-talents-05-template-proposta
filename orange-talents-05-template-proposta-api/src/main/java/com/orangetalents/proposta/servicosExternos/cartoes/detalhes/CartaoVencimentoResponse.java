package com.orangetalents.proposta.servicosExternos.cartoes.detalhes;

import java.time.LocalDateTime;

public class CartaoVencimentoResponse {
    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    public CartaoVencimentoResponse(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

}
