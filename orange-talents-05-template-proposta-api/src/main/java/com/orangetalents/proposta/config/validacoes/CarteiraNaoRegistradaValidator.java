package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.carteiras.Carteira;
import com.orangetalents.proposta.carteiras.CarteiraRepository;
import com.orangetalents.proposta.carteiras.FormCarteiraRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CarteiraNaoRegistradaValidator implements ConstraintValidator<CarteiraNaoRegistrada, FormCarteiraRequest> {
    @Autowired
    private CarteiraRepository repository;

    @Override
    public boolean isValid(FormCarteiraRequest form, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Carteira> talvezCarteira = repository.findByNomeCarteiraAndNumCartao(form.getNomeCarteira(), form.getNumCartao());
        return talvezCarteira.isEmpty();
    }
}
