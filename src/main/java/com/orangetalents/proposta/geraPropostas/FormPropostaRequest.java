package com.orangetalents.proposta.geraPropostas;

import com.orangetalents.proposta.compartilhado.validacoes.CpfOrCnpj;
import com.orangetalents.proposta.compartilhado.validacoes.UniqueValue;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FormPropostaRequest {
    @CpfOrCnpj
    @NotBlank
    @UniqueValue(domainClass = Proposta.class, fieldName = "documento")
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    private String endereco;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal salario;

    public FormPropostaRequest(String documento, String email, String nome, String sobrenome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta converter() {
        return new Proposta(documento, email, nome, sobrenome, endereco, salario);
    }
}
