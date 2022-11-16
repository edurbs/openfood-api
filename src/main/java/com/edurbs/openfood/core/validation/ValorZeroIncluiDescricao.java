package com.edurbs.openfood.core.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class })

public @interface ValorZeroIncluiDescricao {
    
    String message() default "descrição obrigatória inválida para valor zero";

    String valorField();
    String descricaoField();
    String descricaoObrigatoria();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };


}
