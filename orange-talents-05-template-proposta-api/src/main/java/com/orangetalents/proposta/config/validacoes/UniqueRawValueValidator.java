package com.orangetalents.proposta.config.validacoes;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class UniqueRawValueValidator implements ConstraintValidator<UniqueRawValue, Object> {
    private String domainAttribute;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueRawValue params) {
        domainAttribute = params.fieldName();
        aClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select 1 from " + aClass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();

        return list.isEmpty();
    }
}
