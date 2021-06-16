package com.orangetalents.proposta.config.validacoes.outros;

import com.orangetalents.proposta.config.exception.httpException.ValidacoesException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ManualValidator {
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private Validator validator;
    private final Logger logger = LoggerFactory.getLogger(ManualValidator.class);

    public void validateObjeto(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidacoesException(violations);
        }
    }
}
