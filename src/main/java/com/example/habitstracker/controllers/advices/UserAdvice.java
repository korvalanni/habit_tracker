package com.example.habitstracker.controllers.advices;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserAdvice {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity userNotFoundHandler(UserNotFoundException userNotFoundException) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<String> userExistHandler(UserExistException userExistException){
        return ResponseEntity.internalServerError().body(userExistException.getMessage());
    }
}
