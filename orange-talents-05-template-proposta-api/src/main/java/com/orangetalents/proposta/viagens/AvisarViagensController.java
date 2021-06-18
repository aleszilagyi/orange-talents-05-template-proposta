package com.orangetalents.proposta.viagens;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.validacoes.CartaoExiste;
import com.orangetalents.proposta.config.validacoes.outros.ForcarValidacao;
import com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisarViagem;
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
    private ForcarValidacao forcarValidacao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @Autowired
    private ViagemRepository repository;
    @Autowired
    private AvisarViagem avisarViagem;

    @PostMapping("{id}")
    @Transactional
    public ResponseEntity avisar(@PathVariable("id") @CartaoExiste String idCartao,
                                 Principal principal, HttpServletRequest request,
                                 @RequestHeader(value = "User-Agent") String userAgent,
                                 @RequestBody FormViagensRequest form) {
        idCartao = encryptEDecrypt.encrypt(idCartao);
        String userIp = request.getRemoteAddr();
        String userId = principal.getName();

        form.insereNumCartao(idCartao);
        //validar manualmente neste ponto para que o usuário receba o feedback das validações com apenas um retorno
        forcarValidacao.validateObjeto(form);
        avisarViagem.avisar(idCartao, form.getDestino().toString(), form.getValidoAte(), encryptEDecrypt);

        ViagemCartao viagemCartao = form.converter(userIp, userId, userAgent);
        repository.save(viagemCartao);

        return ResponseEntity.ok().build();
    }
}
