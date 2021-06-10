package com.orangetalents.biometria.criarBiometria;

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
@RequestMapping("/biometria")
public class BiometriaController {
    @Autowired
    private BiometriaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastra(@RequestBody @Valid FormBiometriaRequest request) {
        Biometria biometria = request.converter();
        repository.save(biometria);

        URI uriRetorno = UriComponentsBuilder.fromPath("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
