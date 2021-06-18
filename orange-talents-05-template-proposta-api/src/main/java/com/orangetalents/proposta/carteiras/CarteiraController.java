package com.orangetalents.proposta.carteiras;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.outros.ForcarValidacao;
import com.orangetalents.proposta.servicosExternos.cartoes.carteira.CadastrarCarteira;
import com.orangetalents.proposta.servicosExternos.cartoes.carteira.CarteiraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/carteira")
@Validated
public class CarteiraController {
    @Autowired
    private CarteiraRepository repository;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private ForcarValidacao forcarValidacao;
    @Autowired
    private CadastrarCarteira cadastrarCarteira;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity cadastrar(@PathVariable("id") @CartaoExiste String idCartao,
                                    Principal principal, HttpServletRequest request,
                                    @RequestHeader(value = "User-Agent") String userAgent,
                                    @RequestBody FormCarteiraRequest form) {
        String numCartao = encryptEDecrypt.encrypt(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();
        form.addNumCartao(numCartao);

        forcarValidacao.validateObjeto(form);
        CarteiraResponse response = cadastrarCarteira.cadastrar(form.getNumCartao(), form.getEmail(), form.getNomeCarteira(), encryptEDecrypt);

        Carteira carteira = form.converter(userIp, userId, userAgent, numCartao, encryptEDecrypt, response.getId());
        repository.save(carteira);

        URI uriRetorno = UriComponentsBuilder.fromPath("/carteira/{idCartao}/{idCarteira}").buildAndExpand(encryptEDecrypt.decrypt(carteira.getNumCartao()), carteira.getId()).toUri();
        return ResponseEntity.created(uriRetorno).build();
    }
}
