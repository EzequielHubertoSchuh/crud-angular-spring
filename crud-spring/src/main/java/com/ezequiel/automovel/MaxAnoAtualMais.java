package com.ezequiel.automovel;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = MaxAnoAtualMaisValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxAnoAtualMais {
    String message() default "O valor máximo para esse campo é {0}";
    int value() default 0;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
