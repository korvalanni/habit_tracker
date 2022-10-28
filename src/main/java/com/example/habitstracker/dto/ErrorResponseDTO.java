package com.example.habitstracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private int codeError;
    private String message;

    public ErrorResponseDTO(int codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }
}
