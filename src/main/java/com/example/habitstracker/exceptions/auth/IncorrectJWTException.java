package com.example.habitstracker.exceptions.auth;

import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.RepresentableException;

/**
 * Кидать это искоючение, когда возникает проблема с парсингом jwt
 */
public class IncorrectJWTException extends RepresentableException {
    public IncorrectJWTException() {
        super("Incorrect JWT", ErrorCodes.INCORRECT_JWT);
    }
}
