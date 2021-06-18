package com.orangetalents.proposta.servicosExternos.cartoes;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.propostas.Proposta;
import com.orangetalents.proposta.propostas.PropostaRepository;
import com.orangetalents.proposta.propostas.StatusAnalise;
import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.servicosExternos.cartoes.detalhes.InformacoesCartaoResponse;
import feign.FeignException;
import io.micrometer.core.annotation.Timed;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ConsultaCartaoScheduler {
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private Tracer tracer;

    @Timed(value = "consulta_proposta_aberta", extraTags = {"emissora", "Mastercard", "banco", "Itau"}, longTask = true)
    @Scheduled(fixedRate = 5000)
    @Modifying
    @Transactional
    public void consultarSeExisteCartaoDisponivel() {
        List<Proposta> propostaEmAberto = propostaRepository.findTop10ByStatusAnaliseAndNumeroCartaoIsNull(StatusAnalise.ELEGIVEL);
        if (!propostaEmAberto.isEmpty()) {
            salvaNumeroCartao(propostaEmAberto);
        }
    }

    private void salvaNumeroCartao(List<Proposta> propostaEmAberto) {
        try {
            propostaEmAberto.forEach(proposta -> {
                ResponseEntity<InformacoesCartaoResponse> informacoesCartao = consultaCartao.consultaRestricaoSolicitante(proposta.getId().toString());
                String numeroCartao = encryptEDecrypt.encrypt(informacoesCartao.getBody().getId());
                proposta.atualizaNumeroCartao(numeroCartao);
                tracer.activeSpan().setTag("propostaId", proposta.getId().toString());
                tracer.activeSpan().setBaggageItem("documento", proposta.getDocumento());
            });
        } catch (FeignException e) {
            //eventualmente o Feign vai lançar uma exception quando o número de cartão ainda não estiver registrado na api externa
            throw new ErroInternoException();
        }
        Thread.currentThread().interrupt();
    }
}
