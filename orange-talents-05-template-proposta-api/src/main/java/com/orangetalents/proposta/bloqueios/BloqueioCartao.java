package com.orangetalents.proposta.bloqueios;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bloqueio_cartao")
public class BloqueioCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String userIp;
    @NotBlank
    private String userAgent;
    @NotBlank
    private String idCartao;
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

    public BloqueioCartao(String userIp, String userAgent, String idCartao, StatusBloqueio statusBloqueio) {
        this.userIp = userIp;
        this.userAgent = userAgent;
        this.idCartao = idCartao;
        this.statusBloqueio = statusBloqueio;
    }

    public UUID getId() {
        return id;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public StatusBloqueio getStatusBloqueio() {
        return statusBloqueio;
    }

    public LocalDateTime getMomentoCriaco() {
        return momentoCriaco;
    }
}
