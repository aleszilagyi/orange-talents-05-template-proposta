package com.orangetalents.biometria.criarBiometria;

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
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String numeroCartao;
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

    public Biometria(String numeroCartao, @NotNull List<byte[]> fingerprint) {
        this.numeroCartao = numeroCartao;
        this.fingerprint.addAll(fingerprint);
    }

    public UUID getId() {
        return id;
    }
}
