package com.orangetalents.proposta.carteiras;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CarteiraNaoRegistrada;
import com.orangetalents.proposta.config.validacoes.payload.PayloadUnprocessableEntityApi;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@CarteiraNaoRegistrada(payload = PayloadUnprocessableEntityApi.class)
public class FormCarteiraRequest {
    @NotNull
    private CarteiraEnum nomeCarteira;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String numCartao;

    public FormCarteiraRequest(CarteiraEnum nomeCarteira, String email) {
        this.nomeCarteira = nomeCarteira;
        this.email = email;
    }

    public Carteira converter(String userIp, String userId, String userAgent, String numCartao, EncryptEDecrypt encryptEDecrypt, String idExterno) {
        this.email = encryptEDecrypt.encrypt(email);
        return new Carteira(userIp, userId, userAgent, numCartao, nomeCarteira, email, idExterno);
    }

    public void addNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraEnum getNomeCarteira() {
        return nomeCarteira;
    }

    public String getNumCartao() {
        return numCartao;
    }
}
