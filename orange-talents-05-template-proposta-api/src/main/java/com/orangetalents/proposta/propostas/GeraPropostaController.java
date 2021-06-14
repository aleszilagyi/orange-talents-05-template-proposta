package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.propostas.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.servicosExternos.analiseFinanceira.ConsultaRestricao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping
    @Transactional
    public ResponseEntity geraProposta(@RequestBody @Valid FormPropostaRequest request, Principal principal) {
        String userAgent = principal.getName();
        String documentoEncodado = encryptEDecrypt.encrypt(request.getDocumento());
        Proposta proposta = request.converter(userAgent, documentoEncodado);
        repository.save(proposta);

        String documentoDecodado = encryptEDecrypt.decrypt(proposta.getDocumento());
        StatusAnalise statusAnalise = consultaRestricao.consulta(documentoDecodado, proposta.getNomeCompleto(), proposta.getId());
        proposta.atualizaStatusAnalise(statusAnalise);

        URI uriRetorno = UriComponentsBuilder.fromPath("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
