package com.orangetalents.proposta.viagens;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class ViagemCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String userIp;
    @NotBlank
    private String userId;
    @NotBlank
    private String userAgent;
    @Embedded
    private ViagemDestino destino;
    @NotNull
    @Future
    private LocalDate validoAte;
    @NotBlank
    private String numCartao;
    @CreationTimestamp
    private LocalDateTime momentoCriacao = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao;

    @Deprecated
    public ViagemCartao() {
    }

    public ViagemCartao(String userIp, String userId, String userAgent, ViagemDestino destino, LocalDate validoAte, String numCartao) {
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.destino = destino;
        this.validoAte = validoAte;
        this.numCartao = numCartao;
    }
}
