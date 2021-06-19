package com.orangetalents.proposta.criarBiometria;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Biometria {
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
    @ElementCollection
    private List<byte[]> fingerprint = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime momentoCriacao = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String userIp, String userId, String userAgent, String numCartao, List<byte[]> fingerprint) {
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.numCartao = numCartao;
        this.fingerprint.addAll(fingerprint);
    }

    public UUID getId() {
        return id;
    }
}
