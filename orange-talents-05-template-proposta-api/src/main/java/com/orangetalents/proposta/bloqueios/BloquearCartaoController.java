package com.orangetalents.proposta.bloqueios;

import com.orangetalents.proposta.servicosExternos.cartoes.BloquearCartao;
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
    private BloquearCartao bloquearCartao;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable("id") String idCartao, Principal principal, HttpServletRequest request) {
        String userIp = request.getRemoteAddr();
        String userAgent = principal.getName();

        FormBloquearCartao formBloquearCartao = bloquearCartao.bloquear(userIp, userAgent, idCartao);
        BloqueioCartao bloqueioCartao = formBloquearCartao.converter();
        repository.save(bloqueioCartao);

        return ResponseEntity.ok().build();
    }
}
