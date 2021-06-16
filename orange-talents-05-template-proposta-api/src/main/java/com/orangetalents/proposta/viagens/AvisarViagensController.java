package com.orangetalents.proposta.viagens;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.CartaoJaRegistrado;
import com.orangetalents.proposta.config.validacoes.outros.ManualValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;

@RestController
@RequestMapping("/api/viagem")
@Validated
public class AvisarViagensController {
    @Autowired
    private ManualValidator manualValidator;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private ViagemRepository repository;

    @PostMapping("{id}")
    @Transactional
    public ResponseEntity avisar(@PathVariable("id") @CartaoExiste @CartaoJaRegistrado(domainClass = ViagemCartao.class, fieldName = "numCartao") String idCartao,
                                 Principal principal, HttpServletRequest request,
                                 @RequestHeader(value = "User-Agent") String userAgent,
                                 @RequestBody FormViagensRequest form) {
        String idCartaoEncrypted = encryptEDecrypt.encrypt(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();

        form.addInformacoesSolicitante(userIp, userId, userAgent, idCartaoEncrypted);
        manualValidator.validateObjeto(form);

        ViagemCartao viagemCartao = form.converter();
        repository.save(viagemCartao);

        return ResponseEntity.ok().build();
    }
}
