package com.example.habitstracker.exceptions;

public class UserNotFoundException extends RepresentableException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %s has not found", id.toString()), ErrorCodes.USER_NOT_FOUND);
    }

    public UserNotFoundException(String username){
        super(String.format("User with username %s has not found", username), ErrorCodes.USER_NOT_FOUND);
    }
}
