package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.constants.ExceptionTextConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это исключение, когда в JWT лежат какие-то некорректные данные
 */
public class IncorrectClaimsException extends RepresentableException {
    public IncorrectClaimsException() {
        super(ExceptionTextConstants.INCORRECT_CLAIMS, ErrorCodes.INCORRECT_CLAIMS);
    }
}
