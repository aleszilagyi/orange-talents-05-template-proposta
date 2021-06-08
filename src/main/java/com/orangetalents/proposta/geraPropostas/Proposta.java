package com.orangetalents.proposta.geraPropostas;

import com.orangetalents.proposta.geraPropostas.endereco.Endereco;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String documento;
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotNull
    @Embedded
    private Endereco endereco;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private StatusAnalise statusAnalise;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String sobrenome, Endereco endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public void atualizaStatusAnalise(StatusAnalise statusAnalise) {
        this.statusAnalise = statusAnalise;
    }

    public String getDocumento() {
        return documento;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }
}
