package com.minegocio.pruebatecnica.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentificationNumberValid implements ConstraintValidator<ValidIdentification, String> {
    @Override
    public void initialize(ValidIdentification constraintAnnotation) {

    }

    @Override
    public boolean isValid(String identificationNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (identificationNumber == null || identificationNumber.isEmpty()) {
            return true; // Dejas que @NotBlank lo maneje si quieres que no esté vacío
        }

        if (identificationNumber.length() != 10) {
            return false;
        }

        int provinceCode = Integer.parseInt(identificationNumber.substring(0, 2));
        int thirdDigit = Character.getNumericValue(identificationNumber.charAt(2));

        if (provinceCode < 1 || provinceCode > 24 || thirdDigit >= 6) {
            return false;
        }

        int[] coefficients = {2,1,2,1,2,1,2,1,2};
        int sum = 0;

        for (int i = 0; i < coefficients.length; i++) {
            int digit = Character.getNumericValue(identificationNumber.charAt(i)) * coefficients[i];
            if (digit >= 10) {
                digit -= 9;
            }
            sum += digit;
        }

        int verifierDigit = Character.getNumericValue(identificationNumber.charAt(9));
        int calculatedVerifierDigit = (sum % 10 == 0) ? 0 : 10 - (sum % 10);

        return verifierDigit == calculatedVerifierDigit;
    }
}
