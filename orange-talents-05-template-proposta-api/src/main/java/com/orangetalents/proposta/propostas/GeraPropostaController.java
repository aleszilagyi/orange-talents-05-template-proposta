package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.metrics.PropostasMetrics;
import com.orangetalents.proposta.config.validacoes.outros.ForcarValidacao;
import com.orangetalents.proposta.servicosExternos.analiseFinanceira.ConsultaRestricao;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.security.Principal;

@Validated
@RestController
@RequestMapping("/api/proposta")
public class GeraPropostaController {
    @Autowired
    private ForcarValidacao forcarValidacao;
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private ConsultaRestricao consultaRestricao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private PropostasMetrics propostasMetrics;
    @Autowired
    private Tracer tracer;
    private final Logger logger = LoggerFactory.getLogger(GeraPropostaController.class);

    @PostMapping
    @Transactional
    public ResponseEntity geraProposta(@RequestBody FormPropostaRequest formRequest,
                                       Principal principal, HttpServletRequest request,
                                       @RequestHeader(value = "User-Agent") String userAgent) {
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();
        forcarValidacao.validateObjeto(formRequest);

        Proposta proposta = formRequest.converter(userAgent, userId, userIp, encryptEDecrypt);
        repository.save(proposta);

        logger.info(String.format("Proposta criada com sucesso: {documento: %s, propostaId: %s, salario: %s}", proposta.getDocumento(), proposta.getId().toString(), proposta.getSalario()));

        propostasMetrics.propostasContador();

        StatusAnalise statusAnalise = consultaRestricao.consulta(proposta.getDocumento(), proposta.getNomeCompleto(), proposta.getId().toString());
        proposta.atualizaStatusAnalise(statusAnalise);

        tracer.activeSpan().setTag("idUser", userId);
        tracer.activeSpan().setBaggageItem("cartao-num", proposta.getNumeroCartao());

        URI uriRetorno = UriComponentsBuilder.fromPath("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
