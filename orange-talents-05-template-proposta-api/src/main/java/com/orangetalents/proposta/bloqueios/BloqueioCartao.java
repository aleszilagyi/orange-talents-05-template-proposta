package com.orangetalents.proposta.bloqueios;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class BloqueioCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String userIp;
    @NotBlank
    private String userId;
    @NotBlank
    private String userAgent;
    @NotBlank
    private String numCartao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusBloqueio statusBloqueio;
    @CreationTimestamp
    private LocalDateTime momentoCriaco = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao;

    @Deprecated
    public BloqueioCartao() {
    }

    public BloqueioCartao(String userIp, String userId, String userAgent, String numCartao, StatusBloqueio statusBloqueio) {
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.numCartao = numCartao;
        this.statusBloqueio = statusBloqueio;
    }

    public UUID getId() {
        return id;
    }

    public String getNumCartao() {
        return numCartao;
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

    public String getUserId() {
        return userId;
    }
}
