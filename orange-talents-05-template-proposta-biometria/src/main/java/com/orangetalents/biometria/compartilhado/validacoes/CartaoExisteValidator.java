package com.orangetalents.biometria.compartilhado.validacoes;

import com.orangetalents.biometria.compartilhado.exception.httpException.ErroInternoException;
import com.orangetalents.biometria.compartilhado.exception.httpException.RecursoNotFoundException;
import com.orangetalents.biometria.criarBiometria.BiometriaRepository;
import com.orangetalents.biometria.servicosExternos.CartaoNaoExisteException;
import com.orangetalents.biometria.servicosExternos.cartoes.ConsultaCartao;
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
        } catch (CartaoNaoExisteException e) {
            throw new RecursoNotFoundException();
        } catch (FeignException e) {
            throw new ErroInternoException();
        }

        return true;
    }
}
