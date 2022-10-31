package com.example.habitstracker.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String username) {
        super(String.format("User with username%s has already registered", username));
    }
}
