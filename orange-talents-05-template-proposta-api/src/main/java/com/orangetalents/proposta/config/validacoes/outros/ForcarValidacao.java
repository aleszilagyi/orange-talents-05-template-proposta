package com.orangetalents.proposta.config.validacoes.outros;

import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ForcarValidacao {
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private Validator validator;
    private final Logger logger = LoggerFactory.getLogger(ForcarValidacao.class);

    //Pode ser usado para forçar uma validação em uma segunda etapa para algum objeto, mas foi
    //deprecado para este projeto depois do fluxo do controller da viagem ser refatorado
    public void validateObjeto(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
