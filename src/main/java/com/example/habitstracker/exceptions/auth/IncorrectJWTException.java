package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.constants.ExceptionTextConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это искоючение, когда возникает проблема с парсингом jwt
 */
public class IncorrectJWTException extends RepresentableException {
    public IncorrectJWTException() {
        super(ExceptionTextConstants.INCORRECT_JWT, ErrorCodes.INCORRECT_JWT);
    }
}
