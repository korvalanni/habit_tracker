package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

public class UserNotFoundException extends RepresentableException {
    public UserNotFoundException(Long id) {
        super(ExceptionTextConstants.USER_NOT_FOUND_ID.formatted(id), ErrorCodes.USER_NOT_FOUND);
    }

    public UserNotFoundException(String username){
        super(ExceptionTextConstants.USER_NOT_FOUND_USERNAME.formatted(username), ErrorCodes.USER_NOT_FOUND);
    }
}
