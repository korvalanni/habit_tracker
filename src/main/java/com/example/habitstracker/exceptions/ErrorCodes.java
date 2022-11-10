package com.example.habitstracker.exceptions;

import com.example.habitstracker.validation.annotations.AtLeastOneDigit;
import com.example.habitstracker.validation.annotations.AtLeastOneLetter;
import com.example.habitstracker.validation.annotations.OnlyLettersDigitsUnderscore;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Коды ошибок
 */
public enum ErrorCodes {
    USER_NOT_FOUND,
    USER_EXISTS,
    HABIT_NOT_FOUND,
    EMPTY_AUTHORIZATION_HEADER,
    INCORRECT_CLAIMS,
    INCORRECT_JWT,
    SKIPPED_AUTHORIZATION_HEADER,
    INCORRECT_LOGIN_PASSWORD,
    AT_LEAST_ONE_LETTER,
    AT_LEAST_ONE_DIGIT,
    ONLY_LETTERS_DIGITS_UNDERSCORE,
    NOT_NULL,
    NOT_BLANK,
    INCORRECT_SIZE,
    UNKNOWN_EXCEPTION,
    MAPPER_NOT_FOUND;

    /**
     * Получить по названию ошибки валидации ее код
     * @param exception Информация о поле в dto и ошибке
     */
    public static ErrorCodes parse(FieldError exception) {
        if(Objects.equals(exception.getCode(), AtLeastOneLetter.class.getSimpleName())) {
            return AT_LEAST_ONE_LETTER;
        }
        if(Objects.equals(exception.getCode(), AtLeastOneDigit.class.getSimpleName())) {
            return AT_LEAST_ONE_DIGIT;
        }
        if(Objects.equals(exception.getCode(), OnlyLettersDigitsUnderscore.class.getSimpleName())) {
            return ONLY_LETTERS_DIGITS_UNDERSCORE;
        }
        if(Objects.equals(exception.getCode(), NotNull.class.getSimpleName())) {
            return NOT_NULL;
        }
        if(Objects.equals(exception.getCode(), NotBlank.class.getSimpleName())) {
            return NOT_BLANK;
        }
        if(Objects.equals(exception.getCode(), Size.class.getSimpleName())) {
            return INCORRECT_SIZE;
        }
        return UNKNOWN_EXCEPTION;
    }
}
