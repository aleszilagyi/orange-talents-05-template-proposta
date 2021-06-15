package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.metrics.PropostasMetrics;
import com.orangetalents.proposta.propostas.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.servicosExternos.analiseFinanceira.ConsultaRestricao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/proposta")
public class GeraPropostaController {
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private ConsultaRestricao consultaRestricao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private PropostasMetrics propostasMetrics;
    private final Logger logger = LoggerFactory.getLogger(GeraPropostaController.class);

    @PostMapping
    @Transactional
    public ResponseEntity geraProposta(@RequestBody @Valid FormPropostaRequest formRequest, Principal principal, HttpServletRequest request, @RequestHeader(value = "User-Agent") String userAgent) {
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();
        String documentoEncodado = encryptEDecrypt.encrypt(formRequest.getDocumento());
        Proposta proposta = formRequest.converter(userAgent, userId, userIp, documentoEncodado);
        repository.save(proposta);

        logger.info(String.format("Proposta criada com sucesso: {documento: %s, propostaId: %s, salario: %s}", proposta.getDocumento(), proposta.getId().toString(), proposta.getSalario()));
        propostasMetrics.propostasContador();

        String documentoDecodado = encryptEDecrypt.decrypt(proposta.getDocumento());
        StatusAnalise statusAnalise = consultaRestricao.consulta(documentoDecodado, proposta.getNomeCompleto(), proposta.getId());
        proposta.atualizaStatusAnalise(statusAnalise);

        URI uriRetorno = UriComponentsBuilder.fromPath("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
