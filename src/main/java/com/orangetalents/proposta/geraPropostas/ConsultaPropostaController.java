package com.orangetalents.proposta.geraPropostas;

import com.orangetalents.proposta.compartilhado.exception.httpException.RecursoNotFoundException;
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
@RequestMapping("/proposta")
public class ConsultaPropostaController {
    @Autowired
    private PropostaRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity consultaProposta(@PathVariable("id") String idProposta) {
        UUID id = VerificaIdPropostaExiste.verifica(idProposta);
        Optional<Proposta> proposta = repository.findById(id);
        if (proposta.isPresent()) {
            PropostaDto propostaDto = new PropostaDto(proposta.get());
            return ResponseEntity.status(HttpStatus.OK).body(propostaDto);
        } else throw new RecursoNotFoundException();
    }
}
