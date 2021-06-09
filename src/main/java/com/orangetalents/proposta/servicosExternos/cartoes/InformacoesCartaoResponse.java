package com.orangetalents.proposta.servicosExternos.cartoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class InformacoesCartaoResponse {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private List<CartaoBloqueiosResponse> bloqueios;
    private List<CartaoAvisosResponse> avisos;
    private List<CartaoCarteirasResponse> carteiras;
    private List<CartaoParcelasResponse> parcelas;
    private BigDecimal limite;
    private CartaoRenegociacaoResponse renegociacao;
    private CartaoVencimentoResponse vencimento;
    private String idProposta;

    public InformacoesCartaoResponse(String id, LocalDateTime emitidoEm, String titular, List<CartaoBloqueiosResponse> bloqueios, List<CartaoAvisosResponse> avisos, List<CartaoCarteirasResponse> carteiras, List<CartaoParcelasResponse> parcelas, BigDecimal limite, CartaoRenegociacaoResponse renegociacao, CartaoVencimentoResponse vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<CartaoBloqueiosResponse> getBloqueios() {
        return bloqueios;
    }

    public List<CartaoAvisosResponse> getAvisos() {
        return avisos;
    }

    public List<CartaoCarteirasResponse> getCarteiras() {
        return carteiras;
    }

    public List<CartaoParcelasResponse> getParcelas() {
        return parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public CartaoRenegociacaoResponse getRenegociacao() {
        return renegociacao;
    }

    public CartaoVencimentoResponse getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
