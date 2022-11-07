package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это исключение, когда приходит запрос без заголовка Authorization
 */
public class SkippedAuthorizationHeaderException extends RepresentableException {
    public SkippedAuthorizationHeaderException() {
        super("You forgot to set Authorization header", ErrorCodes.SKIPPED_AUTHORIZATION_HEADER);
    }
}
