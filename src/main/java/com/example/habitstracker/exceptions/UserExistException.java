package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

public class UserExistException extends RepresentableException {
    public UserExistException(String username) {
        super(ExceptionTextConstants.USER_ALREADY_EXISTS.formatted(username), ErrorCodes.USER_EXISTS);
    }
}
