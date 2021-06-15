package com.orangetalents.proposta.bloqueios;

public class FormBloquearCartao {

    private String idCartao;
    private String userIp;
    private String userId;
    private String userAgent;
    private StatusBloqueio statusBloqueio;

    public FormBloquearCartao(String idCartao, String userIp, String userId, String userAgent, StatusBloqueio statusBloqueio) {
        this.idCartao = idCartao;
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.statusBloqueio = statusBloqueio;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public BloqueioCartao converter() {
        return new BloqueioCartao(userIp, userAgent, idCartao, statusBloqueio);
    }
}
