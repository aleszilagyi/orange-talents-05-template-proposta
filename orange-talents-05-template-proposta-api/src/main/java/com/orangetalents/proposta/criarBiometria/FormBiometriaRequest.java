package com.orangetalents.proposta.criarBiometria;

import com.orangetalents.proposta.config.validacoes.IsBase64;
import com.orangetalents.proposta.config.validacoes.UniqueRawValue;
import com.orangetalents.proposta.config.validacoes.payload.PayloadUnprocessableEntityApi;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class FormBiometriaRequest {
    @NotNull
    private List<@NotNull @IsBase64 String> fingerprint;
    @UniqueRawValue(payload = {PayloadUnprocessableEntityApi.class}, fieldName = "numCartao", domainClass = Biometria.class)
    private String numCartao;

    public void setFingerprint(List<String> fingerprint) {
        this.fingerprint = fingerprint;
    }

    public void addNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public Biometria converter(String userIp, String userId, String userAgent) {
        return new Biometria(userIp, userId, userAgent, numCartao, transformaEmByte());
    }

    private List<byte[]> transformaEmByte() {
        return this.fingerprint.stream().map(String::getBytes).collect(Collectors.toList());
    }

    public List<String> getFingerprint() {
        return fingerprint;
    }
}
