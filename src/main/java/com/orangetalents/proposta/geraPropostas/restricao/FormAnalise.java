package com.orangetalents.proposta.geraPropostas.restricao;

import com.orangetalents.proposta.geraPropostas.Proposta;

import javax.validation.constraints.NotBlank;

public class FormAnalise {
    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotBlank
    private String idProposta;
    private StatusRestricao resultadoSolicitacao;

    @Deprecated
    public FormAnalise() {
    }

    public FormAnalise(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNomeCompleto();
        this.idProposta = proposta.getId().toString();
    }

    public FormAnalise(String documento, String nome, String idProposta, StatusRestricao resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public StatusRestricao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
