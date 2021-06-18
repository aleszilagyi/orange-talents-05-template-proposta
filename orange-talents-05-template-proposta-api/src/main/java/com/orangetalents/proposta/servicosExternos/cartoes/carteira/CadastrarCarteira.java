package com.orangetalents.proposta.servicosExternos.cartoes.carteira;

import com.orangetalents.proposta.carteiras.CarteiraEnum;
import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisarViagemRequest;
import com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisoViagemResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CadastrarCarteira {

    @Autowired
    private ConsultaCartao consultaCartao;
    private final Logger logger = LoggerFactory.getLogger(com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisarViagem.class);

    public CarteiraResponse cadastrar(String idCartaoEncrypted, String email, CarteiraEnum nomeCarteira, EncryptEDecrypt encryptEDecrypt) {
        try {
            String idCartao = encryptEDecrypt.decrypt(idCartaoEncrypted);
            CarteiraRequest request = new CarteiraRequest(email, nomeCarteira.toString());
            ResponseEntity<CarteiraResponse> response = consultaCartao.cadastrarCarteira(idCartao, request);
            return response.getBody();
        } catch (FeignException e) {
            e.printStackTrace();
            throw new ErroInternoException();
        }
    }
}

