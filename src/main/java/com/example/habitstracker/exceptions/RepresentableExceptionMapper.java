package com.example.habitstracker.exceptions;

import com.example.openapi.dto.ErrorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RepresentableExceptionMapper {
    public ErrorResponseDTO toDTO(RepresentableException exception) {
        return new ErrorResponseDTO().codeError(exception.getCode()).message(exception.getMessage());
    }
}
