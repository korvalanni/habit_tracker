package com.example.habitstracker.validation.validators;

import com.example.habitstracker.validation.annotations.OnlyLettersDigitsUnderscore;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для полей, которые помечены аннотацией {@link OnlyLettersDigitsUnderscore}
 */
public class OnlyLettersDigitsUnderscoreValidator implements ConstraintValidator<OnlyLettersDigitsUnderscore, String> {
    @Override
    public void initialize(OnlyLettersDigitsUnderscore constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        for(char character : value.toCharArray()) {
            if(!Character.isLetter(character) && !Character.isDigit(character) && (character != '_'))
                return false;
        }
        return true;
    }
}
