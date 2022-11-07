package com.example.habitstracker.exceptions;

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
    INCORRECT_LOGIN_PASSWORD
}
