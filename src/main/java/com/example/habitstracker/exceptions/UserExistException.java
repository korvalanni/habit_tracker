package com.example.habitstracker.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String nickName) {
        super(String.format("User with nickname%s has already registered", nickName));
    }
}
