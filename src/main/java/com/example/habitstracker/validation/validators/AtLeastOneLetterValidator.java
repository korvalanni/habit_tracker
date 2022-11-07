package com.example.habitstracker.validation.validators;

import com.example.habitstracker.validation.annotations.AtLeastOneLetter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtLeastOneLetterValidator implements ConstraintValidator<AtLeastOneLetter, String> {
    @Override
    public void initialize(AtLeastOneLetter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        for(char character : value.toCharArray()) {
            if(Character.isLetter(character))
                return true;
        }
        return false;
    }
}
