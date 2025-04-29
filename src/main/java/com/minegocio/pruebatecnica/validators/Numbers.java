package com.minegocio.pruebatecnica.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Numbers implements ConstraintValidator<OnlyNumbers, String> {
    @Override
    public void initialize(OnlyNumbers constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return true;
            // O false, depende si quieres permitir nulos/vacíos
            // Si quieres forzar obligatorio, combínalo con @NotNull aparte
        }

        return s.matches("\\d+");
    }
}
