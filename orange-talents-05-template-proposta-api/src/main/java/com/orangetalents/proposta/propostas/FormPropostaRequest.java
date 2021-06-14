package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.validacoes.CpfOrCnpj;
import com.orangetalents.proposta.config.validacoes.UniqueDocumento;
import com.orangetalents.proposta.propostas.endereco.FormEnderecoRequest;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FormPropostaRequest {
    @CpfOrCnpj
    @NotBlank
    @UniqueDocumento
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotNull
    private FormEnderecoRequest endereco;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal salario;

    public FormPropostaRequest(String documento, String email, String nome, String sobrenome, FormEnderecoRequest endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta converter(String userAgent, String userIp, String documentoEncodado) {
        return new Proposta(userAgent, userIp, documentoEncodado, email, nome, sobrenome, endereco.converter(), salario);
    }

    public String getDocumento() {
        return documento;
    }
}
