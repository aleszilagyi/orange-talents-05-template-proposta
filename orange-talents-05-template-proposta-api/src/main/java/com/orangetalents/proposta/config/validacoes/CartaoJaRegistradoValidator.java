package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import com.orangetalents.proposta.config.exception.httpException.OperacaoNaoPodeSerRealizadaException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CartaoJaRegistradoValidator implements ConstraintValidator<CartaoJaRegistrado, String> {
    private String domainAttribute;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;

    @Override
    public void initialize(CartaoJaRegistrado constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String cartao = encryptEDecrypt.encrypt(s);
        Query query = manager.createQuery("select 1 from " + aClass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", cartao);
        List<?> list = query.getResultList();

        if (list.isEmpty()) return true;

        else throw new OperacaoNaoPodeSerRealizadaException(domainAttribute + " já está registrado no sistema");
    }
}