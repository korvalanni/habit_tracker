package com.example.habitstracker.exceptions;

public class UserExistException extends RepresentableException {
    public UserExistException(String username) {
        super(String.format("User with username %s has already registered", username), ErrorCodes.USER_EXISTS);
    }
}
