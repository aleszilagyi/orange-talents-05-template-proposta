package com.orangetalents.proposta.carteiras;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String userIp;
    private String userId;
    private String userAgent;
    @NotBlank
    private String numCartao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraEnum nomeCarteira;
    @NotBlank
    private String email;
    private String idExterno;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String userIp, String userId, String userAgent, String numCartao, CarteiraEnum nomeCarteira, String email, String idExterno) {
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.numCartao = numCartao;
        this.nomeCarteira = nomeCarteira;
        this.email = email;
        this.idExterno = idExterno;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public UUID getId() {
        return id;
    }
}
