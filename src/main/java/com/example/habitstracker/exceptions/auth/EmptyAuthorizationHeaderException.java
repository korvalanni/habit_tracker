package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;
import org.springframework.http.HttpHeaders;

/**
 * Кидать это исключение в случае, когда в запросе нет значения в заголовке Authorization
 */
public class EmptyAuthorizationHeaderException extends RepresentableException {
    public EmptyAuthorizationHeaderException() {
        super("You must fill value of the " + HttpHeaders.AUTHORIZATION + " header", ErrorCodes.EMPTY_AUTHORIZATION_HEADER);
    }
}
