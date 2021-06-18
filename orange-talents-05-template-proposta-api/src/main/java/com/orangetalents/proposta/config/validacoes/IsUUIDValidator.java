package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.exception.httpException.RecursoNotFoundException;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.UUID;

public class IsUUIDValidator implements ConstraintValidator<IsUUID, String> {
    private String domainAttribute;
    private Class<?> aClass;
    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(IsUUID constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        UUID id = null;
        try {
            id = UUID.fromString(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RecursoNotFoundException();
        }

        Query query = manager.createQuery("select 1 from " + aClass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", id);
        List<?> list = query.getResultList();
        Assert.state(list.size() <= 1, "Foi encontrado(a) mais de um(a) " + aClass + " com o atributo " + domainAttribute + " = " + id);

        if (list.isEmpty()) throw new RecursoNotFoundException();
        return true;
    }
}
