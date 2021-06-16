package com.orangetalents.proposta.viagens;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.UniqueValue;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FormViagensRequest {
    private String userIp;
    private String userId;
    private String userAgent;
    private DestinoDto destino;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;
    @NotBlank
    @UniqueValue(domainClass = ViagemCartao.class, fieldName = "numCartao")
    private String numCartao;

    public void addInformacoesSolicitante(String userIp, String userId, String userAgent, String numCartao) {
        this.userIp = userIp;
        this.userId = userId;
        this.userAgent = userAgent;
        this.numCartao = numCartao;
    }

    public void setDestino(DestinoDto destino) {
        this.destino = destino;
    }

    //Não tem como fazer datas com o constructor, e o destino iria ficar sozinho e daria problema também no Jackson, setters necessários
    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    public ViagemCartao converter( ) {
        return new ViagemCartao(userIp, userId, userAgent, destino.converter(), validoAte, numCartao);
    }
}
