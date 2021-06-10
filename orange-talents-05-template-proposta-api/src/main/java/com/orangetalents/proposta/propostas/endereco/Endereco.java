package com.orangetalents.proposta.propostas.endereco;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
public class Endereco {
    @NotBlank
    private String logradouro;
    @NotNull
    private int numero;
    private String complemento;
    @NotBlank
    private String cidade;
    @NotBlank
    private String pais;
    @NotBlank
    private String estado;
    @NotBlank
    private String cep;

    @Deprecated
    public Endereco() {
    }

    public Endereco(String logradouro, int numero, String complemento, String cidade, String pais, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.estado = estado;
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }
}
