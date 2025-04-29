package com.minegocio.pruebatecnica.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IdentificationNumberValid.class)

public @interface ValidIdentification {
    String message() default "La cedula no es valida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
