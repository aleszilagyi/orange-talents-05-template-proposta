package com.orangetalents.proposta.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {CarteiraNaoRegistradaValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CarteiraNaoRegistrada {
    String message() default "carteira já está registrada no sistema";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
