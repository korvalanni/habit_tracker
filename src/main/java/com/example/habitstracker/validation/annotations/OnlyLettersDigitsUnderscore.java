package com.example.habitstracker.validation.annotations;

import com.example.habitstracker.validation.validators.AtLeastOneLetterValidator;
import com.example.habitstracker.validation.validators.OnlyLettersDigitsUnderscoreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация, которая позволяет проверить, что в поле содержится только буквы, цифры и нижнее подчеркивание
 */
@Documented
@Constraint(validatedBy = OnlyLettersDigitsUnderscoreValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLettersDigitsUnderscore {
    String message() default "Field can contains only letters, digits or underscore";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
