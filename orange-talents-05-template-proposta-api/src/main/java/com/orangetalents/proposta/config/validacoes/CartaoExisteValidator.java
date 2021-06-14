package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.exception.httpException.ErroInternoException;
import com.orangetalents.proposta.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CartaoExisteValidator implements ConstraintValidator<CartaoExiste, String> {
    @Autowired
    private ConsultaCartao consultaCartao;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String idCartao = consultaCartao.consultaCartao(s).getBody().getId();
        } catch (FeignException e) {
            throw new ErroInternoException();
        }

        return true;
    }
}