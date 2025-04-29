package com.minegocio.pruebatecnica.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = Numbers.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface OnlyNumbers {
    String message() default "El campo solo puede contener números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
