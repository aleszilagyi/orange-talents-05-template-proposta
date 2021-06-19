package com.orangetalents.proposta.criarBiometria;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.outros.ForcarValidacao;
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
@RequestMapping("/api/biometria")
public class BiometriaController {
    @Autowired
    private BiometriaRepository repository;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private ForcarValidacao forcarValidacao;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity cadastrarBiometria(@PathVariable @CartaoExiste String idCartao,
                                             Principal principal, HttpServletRequest request,
                                             @RequestHeader(value = "User-Agent") String userAgent,
                                             @RequestBody FormBiometriaRequest formRequest) {
        String cartaoEncrypted = encryptEDecrypt.encrypt(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();

        formRequest.addNumCartao(cartaoEncrypted);
        forcarValidacao.validateObjeto(formRequest);

        Biometria biometria = formRequest.converter(userIp, userId, userAgent);
        repository.save(biometria);

        URI uriRetorno = UriComponentsBuilder.fromPath("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
