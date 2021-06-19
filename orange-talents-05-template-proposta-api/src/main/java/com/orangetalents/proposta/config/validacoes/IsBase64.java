package com.orangetalents.proposta.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {IsBase64Validator.class})
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsBase64 {
    String message() default "formato inválido para o fingerprint";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
