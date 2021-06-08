package com.orangetalents.proposta.geraPropostas.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FormEnderecoRequest {
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

    public FormEnderecoRequest(String logradouro, int numero, String complemento, String cidade, String pais, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco converter() {
        return new Endereco(logradouro, numero, complemento, cidade, pais, estado, cep);
    }
}
