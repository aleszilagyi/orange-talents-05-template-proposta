package com.orangetalents.biometria.servicosExternos.cartoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoRenegociacaoResponse {
    private String id;
    private int quantidade;
    private BigDecimal valor;
    private LocalDateTime dataDeCriacao;

    public CartaoRenegociacaoResponse(String id, int quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}
