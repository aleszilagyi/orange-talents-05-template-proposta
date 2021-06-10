package com.orangetalents.proposta.propostas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orangetalents.proposta.servicosExternos.analiseFinanceira.ConsultaRestricao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class GeraPropostaController {
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private ConsultaRestricao consultaRestricao;

    @PostMapping
    @Transactional
    public ResponseEntity geraProposta(@RequestBody @Valid FormPropostaRequest request) throws JsonProcessingException {
        Proposta proposta = request.converter();
        repository.save(proposta);

        StatusAnalise statusAnalise = consultaRestricao.consulta(proposta);
        proposta.atualizaStatusAnalise(statusAnalise);

        URI uriRetorno = UriComponentsBuilder.fromPath("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
