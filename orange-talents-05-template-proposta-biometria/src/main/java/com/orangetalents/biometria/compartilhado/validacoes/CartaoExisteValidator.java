package com.orangetalents.biometria.compartilhado.validacoes;

import com.orangetalents.biometria.compartilhado.exception.httpException.ErroInternoException;
import com.orangetalents.biometria.compartilhado.exception.httpException.RecursoNotFoundException;
import com.orangetalents.biometria.criarBiometria.Biometria;
import com.orangetalents.biometria.criarBiometria.BiometriaRepository;
import com.orangetalents.biometria.servicosExternos.CartaoNaoExisteException;
import com.orangetalents.biometria.servicosExternos.cartoes.ConsultaCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CartaoExisteValidator implements ConstraintValidator<CartaoExiste, String> {
    @Autowired
    private BiometriaRepository repository;
    @Autowired
    private ConsultaCartao consultaCartao;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Biometria> biometria = repository.findByNumeroCartao(s);
        if (biometria.isEmpty()) {
            try {
                String idCartao = consultaCartao.consultaCartao(s).getBody().getId();
            } catch (CartaoNaoExisteException e) {
                throw new RecursoNotFoundException();
            } catch (FeignException e) {
                throw new ErroInternoException();
            }
        }
        return true;
    }
}
