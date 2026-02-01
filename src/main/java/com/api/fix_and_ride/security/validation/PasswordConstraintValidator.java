package com.api.fix_and_ride.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator
        implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext context) {

        if (password == null) {
            return false;
        }

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");
        boolean validLength = password.length() >= 8
                && password.length() <= 20;

        return hasUppercase
                && hasLowercase
                && hasDigit
                && hasSpecialChar
                && validLength;
    }
}
