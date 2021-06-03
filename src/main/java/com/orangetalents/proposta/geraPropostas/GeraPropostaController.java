package com.orangetalents.proposta.geraPropostas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class GeraPropostaController {
    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity geraProposta(@RequestBody @Valid FormPropostaRequest request) {
        Proposta proposta = request.converter();
        manager.persist(proposta);

        URI uriRetorno = UriComponentsBuilder.fromPath("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
