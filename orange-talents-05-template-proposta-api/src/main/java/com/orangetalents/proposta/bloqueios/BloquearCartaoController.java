package com.orangetalents.proposta.bloqueios;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.UniqueEncryptedValue;
import com.orangetalents.proposta.config.validacoes.outros.ForcarValidacao;
import com.orangetalents.proposta.config.validacoes.payload.PayloadUnprocessableEntityApi;
import com.orangetalents.proposta.servicosExternos.cartoes.bloquear.BloquearCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;

@Validated
@RestController
@RequestMapping("/api/bloquear")
public class BloquearCartaoController {
    @Autowired
    private BloqueioRepository repository;
    @Autowired
    private ForcarValidacao forcarValidacao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private BloquearCartao bloquearCartao;
    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable @CartaoExiste @UniqueEncryptedValue(payload = {PayloadUnprocessableEntityApi.class}, fieldName = "numCartao", domainClass = BloqueioCartao.class) String idCartao,
                                   Principal principal, HttpServletRequest request,
                                   @RequestHeader(value = "User-Agent") String userAgent) {
        String cartaoEncrypted = encryptEDecrypt.encrypt(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();

        StatusBloqueio statusBloqueio = bloquearCartao.bloquear(userIp, userId, userAgent, cartaoEncrypted, encryptEDecrypt);

        BloqueioCartao bloqueioCartao = new BloqueioCartao(userIp, userId, userAgent, cartaoEncrypted, statusBloqueio);

        repository.save(bloqueioCartao);

        logger.info(String.format("Bloqueio realizado com sucesso: {idCartao: %s, idBloqueio: %s, em: %s, status: %s}",
                bloqueioCartao.getNumCartao(),
                bloqueioCartao.getId().toString(),
                bloqueioCartao.getMomentoCriaco().toString(),
                bloqueioCartao.getStatusBloqueio().toString()));

        return ResponseEntity.ok().body(new BloquearCartaoResponse(bloqueioCartao, encryptEDecrypt));
    }
}
