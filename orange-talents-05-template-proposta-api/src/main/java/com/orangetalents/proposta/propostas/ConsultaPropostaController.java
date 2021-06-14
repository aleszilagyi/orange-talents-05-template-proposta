package com.orangetalents.proposta.propostas;

import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import com.orangetalents.proposta.propostas.encrypt.EncryptEDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/proposta")
public class ConsultaPropostaController {
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;

    @GetMapping("/{id}")
    public ResponseEntity consultaProposta(@PathVariable("id") String idProposta) {
        UUID id = VerificaIdPropostaExiste.verifica(idProposta);
        Optional<Proposta> proposta = repository.findById(id);
        if (proposta.isEmpty()) {
            throw new RecursoNotFoundException();
        }
        String documentoDesfocado = encryptEDecrypt.ofuscarDocumento(proposta.get().getDocumento());
        PropostaDto propostaDto = new PropostaDto(proposta.get(), documentoDesfocado);
        return ResponseEntity.status(HttpStatus.OK).body(propostaDto);
    }
}
