package com.orangetalents.proposta.propostas.endereco;

public class EnderecoDto {
    private String logradouro;
    private int numero;
    private String complemento;
    private String cidade;
    private String pais;
    private String estado;
    private String cep;

    public EnderecoDto(Endereco endereco) {
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.cidade = endereco.getCidade();
        this.pais = endereco.getPais();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
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
