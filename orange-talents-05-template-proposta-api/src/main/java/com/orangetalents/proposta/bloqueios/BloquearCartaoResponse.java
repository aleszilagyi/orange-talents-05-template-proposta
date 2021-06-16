package com.orangetalents.proposta.bloqueios;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;

import java.time.LocalDateTime;

public class BloquearCartaoResponse {

    private String idCartao;
    private String userId;
    private StatusBloqueio statusBloqueio;
    private LocalDateTime momentoCriaco;
    private LocalDateTime ultimaAtualizacao;

    public BloquearCartaoResponse(BloqueioCartao bloqueioCartao, EncryptEDecrypt encryptEDecrypt) {
        this.idCartao = encryptEDecrypt.ofuscar(bloqueioCartao.getNumCartao());
        this.userId = bloqueioCartao.getUserId();
        this.statusBloqueio = bloqueioCartao.getStatusBloqueio();
        this.momentoCriaco = bloqueioCartao.getMomentoCriaco();
        this.ultimaAtualizacao =bloqueioCartao.getUltimaAtualizacao();
    }

    public String getIdCartao() {
        return idCartao;
    }

    public String getUserId() {
        return userId;
    }

    public StatusBloqueio getStatusBloqueio() {
        return statusBloqueio;
    }

    public LocalDateTime getMomentoCriaco() {
        return momentoCriaco;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
}
