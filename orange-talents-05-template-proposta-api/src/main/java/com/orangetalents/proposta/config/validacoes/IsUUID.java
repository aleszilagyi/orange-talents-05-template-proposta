package com.orangetalents.proposta.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {IsUUIDValidator.class})
@Target({ElementType.TYPE_PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsUUID {
    String message() default "Proposta fornecida não existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
