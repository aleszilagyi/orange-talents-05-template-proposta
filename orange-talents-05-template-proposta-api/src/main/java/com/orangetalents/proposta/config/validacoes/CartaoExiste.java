package com.orangetalents.proposta.config.validacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {CartaoExisteValidator.class})
@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CartaoExiste {
    String message() default "cartão não encontrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
