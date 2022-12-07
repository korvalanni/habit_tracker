package com.example.habitstracker.advices;

import com.example.habitstracker.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.openapi.dto.ErrorResponseDTO;

import java.util.List;

/**
 * Обработчики ошибок
 */
@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler({HabitNotFoundException.class, UserExistException.class, UserNotFoundException.class,
            HabitPermissionException.class})
    public ResponseEntity<ErrorResponseDTO> exceptionHandler(RepresentableException exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO()
                .codeError(exception.getCode())
                .message(exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    /**
     * Обработка ошибок валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDTO>> methodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        List<ErrorResponseDTO> errors = methodArgumentNotValidException
                .getFieldErrors()
                .stream()
                .map(error -> {
                    String code = ErrorCodes.parse(error).getCode();
                    String message = error.getDefaultMessage();
                    return new ErrorResponseDTO().codeError(code).message(message);
                })
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }
}
