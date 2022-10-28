package com.example.habitstracker.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id%s has not found", id.toString()));
    }
}
