package com.orangetalents.proposta.viagens;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orangetalents.proposta.config.validacoes.UniqueRawValue;
import com.orangetalents.proposta.config.validacoes.payload.PayloadUnprocessableEntityApi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FormViagensRequest {
    @Valid
    private DestinoDto destino;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;
    @UniqueRawValue(payload = {PayloadUnprocessableEntityApi.class}, domainClass = ViagemCartao.class, fieldName = "numCartao")
    private String numCartao;

    public void setDestino(DestinoDto destino) {
        this.destino = destino;
    }

    public void insereNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    //Não tem como fazer datas entrarem com o construtor, e o destino iria ficar sozinho e daria problema também no Jackson, setters necessários
    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    public ViagemCartao converter(String userIp, String userId, String userAgent) {
        return new ViagemCartao(userIp, userId, userAgent, destino.converter(), validoAte, numCartao);
    }

    public DestinoDto getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
