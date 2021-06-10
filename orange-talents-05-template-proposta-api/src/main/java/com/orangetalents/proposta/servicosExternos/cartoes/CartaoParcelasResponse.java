package com.orangetalents.proposta.servicosExternos.cartoes;

import java.math.BigDecimal;

public class CartaoParcelasResponse {
    private String id;
    private int quantidade;
    private BigDecimal valor;

    public CartaoParcelasResponse(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
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
}
