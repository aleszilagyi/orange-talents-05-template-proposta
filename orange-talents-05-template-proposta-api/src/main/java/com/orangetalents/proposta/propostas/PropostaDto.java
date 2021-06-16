package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.propostas.endereco.EnderecoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PropostaDto {
    private UUID id;
    private String documento;
    private String email;
    private String nome;
    private String sobrenome;
    private EnderecoDto endereco;
    private BigDecimal salario;
    private StatusAnalise statusAnalise;
    private String numeroCartao;
    private LocalDateTime momentoCriacao;
    private LocalDateTime ultimaAtualizacao;

    public PropostaDto(Proposta proposta, EncryptEDecrypt encryptEDecrypt) {
        this.id = proposta.getId();
        this.documento = encryptEDecrypt.ofuscar(proposta.getDocumento());
        this.email = encryptEDecrypt.ofuscar(proposta.getEmail());
        this.nome = proposta.getNome();
        this.sobrenome = proposta.getSobrenome();
        this.endereco = new EnderecoDto(proposta.getEndereco());
        this.salario = proposta.getSalario();
        this.statusAnalise = proposta.getStatusAnalise();
        this.numeroCartao = encryptEDecrypt.decrypt(proposta.getNumeroCartao());
        this.momentoCriacao = proposta.getMomentoCriacao();
        this.ultimaAtualizacao = proposta.getUltimaAtualizacao();
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
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

    public EnderecoDto getEndereco() {
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
