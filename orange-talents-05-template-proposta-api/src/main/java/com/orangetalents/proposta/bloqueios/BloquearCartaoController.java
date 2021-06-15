package com.orangetalents.proposta.bloqueios;

import com.orangetalents.proposta.config.validacoes.outros.OutrosValidators;
import com.orangetalents.proposta.servicosExternos.cartoes.BloquearCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;

@RestController
@RequestMapping("/api/bloquear")
@Validated
public class BloquearCartaoController {
    @Autowired
    private BloqueioRepository repository;
    @Autowired
    private OutrosValidators outrosValidators;
    @Autowired
    private BloquearCartao bloquearCartao;
    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable("id") String idCartao, Principal principal, HttpServletRequest request, @RequestHeader(value = "User-Agent") String userAgent) {
        outrosValidators.cartaoExisteApiExterna(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();

        FormBloquearCartao formBloquearCartao = bloquearCartao.bloquear(userIp, userId, userAgent, idCartao);
        BloqueioCartao bloqueioCartao = formBloquearCartao.converter();
        repository.save(bloqueioCartao);

        logger.info(String.format("Bloqueio realizado com sucesso: {idCartao: %s, idBloqueio: %s, em: %s, status: %s}",
                bloqueioCartao.getIdCartao(),
                bloqueioCartao.getId().toString(),
                bloqueioCartao.getMomentoCriaco().toString(),
                bloqueioCartao.getStatusBloqueio().toString()));

        return ResponseEntity.ok().build();
    }
}
