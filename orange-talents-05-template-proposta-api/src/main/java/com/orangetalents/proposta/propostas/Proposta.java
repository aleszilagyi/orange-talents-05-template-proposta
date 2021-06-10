package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.propostas.endereco.Endereco;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Column(nullable = true, updatable = true)
    private String numeroCartao;
    @CreationTimestamp
    private LocalDateTime momentoCriacao = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao;

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

    public String getDocumento() {
        return documento;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public void atualizaStatusAnalise(StatusAnalise statusAnalise) {
        this.statusAnalise = statusAnalise;
    }

    public void atualizaNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusAnalise getStatusAnalise() {
        return statusAnalise;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getMomentoCriacao() {
        return momentoCriacao;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
}
