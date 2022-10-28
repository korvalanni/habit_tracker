package com.example.habitstracker.advices;

import com.example.habitstracker.dto.ErrorResponseDTO;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserAdvice {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundHandler(UserNotFoundException userNotFoundException) {
        String message = userNotFoundException.getMessage();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ErrorCodes.USER_NOT_FOUND.ordinal(),message);
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<ErrorResponseDTO> userExistHandler(UserExistException userExistException){
        String message = userExistException.getMessage();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ErrorCodes.USER_EXISTS.ordinal(),message);
        return ResponseEntity.internalServerError().body(errorResponseDTO);
    }
}
