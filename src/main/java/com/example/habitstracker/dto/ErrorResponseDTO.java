package com.example.habitstracker.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorResponseDTO {
    private int codeError;
    private String message;

    public ErrorResponseDTO(int codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }
}
