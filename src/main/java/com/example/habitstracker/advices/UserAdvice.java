package com.example.habitstracker.advices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.openapi.dto.ErrorResponseDTO;

@ControllerAdvice
public class UserAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundHandler(UserNotFoundException userNotFoundException) {
        String message = userNotFoundException.getMessage();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO()
                .codeError(ErrorCodes.USER_NOT_FOUND.ordinal())
                .message(message);
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponseDTO> userExistHandler(UserExistException userExistException){
        String message = userExistException.getMessage();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO()
                .codeError(ErrorCodes.USER_EXISTS.ordinal())
                .message(message);
        return ResponseEntity.internalServerError().body(errorResponseDTO);
    }

    @ExceptionHandler(HabitNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> habitNotFound(HabitNotFoundException habitNotFoundException) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO()
                .codeError(ErrorCodes.HABIT_NOT_FOUND.ordinal())
                .message(habitNotFoundException.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
}
