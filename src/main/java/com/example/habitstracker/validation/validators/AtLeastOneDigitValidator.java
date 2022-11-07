package com.example.habitstracker.validation.validators;

import com.example.habitstracker.validation.annotations.AtLeastOneDigit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtLeastOneDigitValidator implements ConstraintValidator<AtLeastOneDigit, String> {
    @Override
    public void initialize(AtLeastOneDigit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        for(char character : value.toCharArray()) {
            if(Character.isDigit(character))
                return true;
        }
        return false;
    }
}
