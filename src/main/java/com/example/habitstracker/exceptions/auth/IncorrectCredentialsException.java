package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это исключение, когда приходят некорректные данные для авторизации
 */
public class IncorrectCredentialsException extends RepresentableException {
    public IncorrectCredentialsException() {
        super("Incorrect login or password", ErrorCodes.INCORRECT_LOGIN_PASSWORD);
    }
}
