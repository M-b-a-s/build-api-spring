package com.mbas.E_commerce.validation;

import jakarta.validation.ConstraintValidator;

public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {
    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Consider null as valid. Use @NotNull for null checks.
        }
        return value.equals(value.toLowerCase());
    }
}
