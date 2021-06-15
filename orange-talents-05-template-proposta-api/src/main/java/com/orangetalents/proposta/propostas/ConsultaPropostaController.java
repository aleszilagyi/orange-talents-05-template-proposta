package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import com.orangetalents.proposta.config.metrics.PropostasMetrics;
import com.orangetalents.proposta.config.validacoes.outros.OutrosValidators;
import com.orangetalents.proposta.propostas.encrypt.EncryptEDecrypt;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/proposta")
public class ConsultaPropostaController {
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private PropostasMetrics propostasMetrics;
    @Autowired
    private OutrosValidators outrosValidators;
    private final Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);

    @GetMapping("/{id}")
    @Timed(value = "consulta_proposta",extraTags = {"emissora", "Mastercard", "banco", "Itau"})
    public ResponseEntity consultaProposta(@PathVariable("id") String idProposta) {
        UUID id = outrosValidators.verificaUUID(idProposta);
        Optional<Proposta> proposta = repository.findById(id);
        if (proposta.isEmpty()) {
            logger.warn("Documento informado não existe = " + idProposta);
            throw new RecursoNotFoundException();
        }
        String documentoDesfocado = encryptEDecrypt.ofuscarDocumento(proposta.get().getDocumento());
        PropostaDto propostaDto = new PropostaDto(proposta.get(), documentoDesfocado);
        return ResponseEntity.status(HttpStatus.OK).body(propostaDto);
    }

}
