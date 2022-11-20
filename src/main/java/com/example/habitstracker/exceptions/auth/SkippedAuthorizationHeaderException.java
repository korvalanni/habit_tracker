package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.constants.ExceptionTextConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это исключение, когда приходит запрос без заголовка Authorization
 */
public class SkippedAuthorizationHeaderException extends RepresentableException {
    public SkippedAuthorizationHeaderException() {
        super(ExceptionTextConstants.SKIPPED_AUTHORIZATION_HEADER, ErrorCodes.SKIPPED_AUTHORIZATION_HEADER);
    }
}
