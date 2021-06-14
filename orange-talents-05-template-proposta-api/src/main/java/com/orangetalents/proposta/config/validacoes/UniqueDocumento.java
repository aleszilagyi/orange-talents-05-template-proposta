package com.orangetalents.proposta.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {UniqueDocumentoValidator.class})
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueDocumento {
    String message() default "documento fornecido já está cadastrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
