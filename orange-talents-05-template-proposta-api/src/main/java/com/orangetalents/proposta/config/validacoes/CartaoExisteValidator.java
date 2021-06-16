package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class CartaoExisteValidator implements ConstraintValidator<CartaoExiste, String> {
    @Autowired
    private ConsultaCartao consultaCartao;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String idCartao = consultaCartao.consultaCartao(value).getBody().getId();
        } catch (FeignException e) {
            throw new ErroInternoException();
        }

        return true;
    }
}