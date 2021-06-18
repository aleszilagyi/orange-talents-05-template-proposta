package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.metrics.PropostasMetrics;
import com.orangetalents.proposta.config.validacoes.IsUUID;
import com.orangetalents.proposta.config.validacoes.payload.PayloadUnprocessableEntityApi;
import io.micrometer.core.annotation.Timed;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
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
    private Tracer tracer;
    private final Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);


    @GetMapping("/{id}")
    @Timed(value = "consulta_proposta", extraTags = {"emissora", "Mastercard", "banco", "Itau"})
    public ResponseEntity consultaProposta(@PathVariable("id") @IsUUID(payload = {PayloadUnprocessableEntityApi.class}, domainClass = Proposta.class, fieldName = "id") String idProposta) {
        UUID id = UUID.fromString(idProposta);
        Proposta proposta = repository.findById(id).get();

        //a ferramenta de decrypt está indo para ao DTO porque eu preciso saber o número do cartão nos testes com a aplicação rodando
        PropostaDto propostaDto = new PropostaDto(proposta, encryptEDecrypt);

        tracer.activeSpan().setTag("propostaId", idProposta);
        tracer.activeSpan().setBaggageItem("cartao-num", proposta.getNumeroCartao());
        return ResponseEntity.status(HttpStatus.OK).body(propostaDto);
    }

}
