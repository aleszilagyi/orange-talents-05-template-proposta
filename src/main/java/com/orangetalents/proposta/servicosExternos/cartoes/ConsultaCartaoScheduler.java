package com.orangetalents.proposta.servicosExternos.cartoes;

import com.orangetalents.proposta.compartilhado.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.geraPropostas.Proposta;
import com.orangetalents.proposta.geraPropostas.PropostaRepository;
import com.orangetalents.proposta.geraPropostas.StatusAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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

    @Scheduled(fixedRate = 5000)
    @Modifying
    @Transactional
    public void consultarSeExisteCartaoDisponivel() {
        List<Proposta> propostaEmAberto = propostaRepository.findTop10ByStatusAnaliseAndNumeroCartaoIsNull(StatusAnalise.ELEGIVEL);
        if (!propostaEmAberto.isEmpty()) {
            salvaNumeroCartao(propostaEmAberto);
        }
    }

    @Async
    private void salvaNumeroCartao(List<Proposta> propostaEmAberto) {
        try {
            propostaEmAberto.forEach(proposta -> {
                ResponseEntity<InformacoesCartaoResponse> informacoesCartao = consultaCartao.consultaRestricaoSolicitante(proposta.getId().toString());
                String numeroCartao = informacoesCartao.getBody().getId();
                proposta.atualizaNumeroCartao(numeroCartao);
            });
        } catch (FeignException e) {
            throw new ErroInternoException();
        }
        Thread.currentThread().interrupt();
    }
}
