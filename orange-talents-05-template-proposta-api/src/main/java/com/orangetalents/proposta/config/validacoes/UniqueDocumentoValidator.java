package com.orangetalents.proposta.config.validacoes;

import com.orangetalents.proposta.config.encrypt.EncryptEDecrypt;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class UniqueDocumentoValidator implements ConstraintValidator<UniqueDocumento, String> {
    @Autowired
    private EncryptEDecrypt encryptEDecrypt;
    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String documentoEncrypted = encryptEDecrypt.encrypt(s);
        Query query = manager.createQuery("select 1 from Proposta p where p.documento =:value");
        query.setParameter("value", documentoEncrypted);
        List<?> list = query.getResultList();

        return list.isEmpty();
    }
}
