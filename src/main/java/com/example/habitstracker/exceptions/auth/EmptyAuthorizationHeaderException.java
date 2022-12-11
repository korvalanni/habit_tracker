package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.constants.ExceptionTextConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это исключение в случае, когда в запросе нет значения в заголовке Authorization
 */
public class EmptyAuthorizationHeaderException extends RepresentableException {
    public EmptyAuthorizationHeaderException() {
        super(ExceptionTextConstants.EMPTY_AUTHORIZATION_HEADER, ErrorCodes.EMPTY_AUTHORIZATION_HEADER);
    }
}
