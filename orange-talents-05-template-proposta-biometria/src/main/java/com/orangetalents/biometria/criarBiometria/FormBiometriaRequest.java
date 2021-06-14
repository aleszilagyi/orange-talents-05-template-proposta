package com.orangetalents.biometria.criarBiometria;

import com.orangetalents.biometria.config.validacoes.CartaoExiste;
import com.orangetalents.biometria.config.validacoes.IsBase64;
import com.orangetalents.biometria.config.validacoes.UniqueValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class FormBiometriaRequest {
    @CartaoExiste
    @NotBlank
    @UniqueValue(domainClass = Biometria.class, fieldName = "numeroCartao", message = "fingerprint(s) já registrado(s)")
    private String numeroCartao;
    @NotNull
    private List<@NotNull @IsBase64 String> fingerprint;

    public FormBiometriaRequest(String numeroCartao, List<String> fingerprint) {
        this.numeroCartao = numeroCartao;
        this.fingerprint = fingerprint;
    }

    public Biometria converter(String userAgent) {
        return new Biometria(userAgent, numeroCartao, transformaEmByte());
    }

    private List<byte[]> transformaEmByte() {
        return this.fingerprint.stream().map(String::getBytes).collect(Collectors.toList());
    }

    public List<String> getFingerprint() {
        return fingerprint;
    }
}
