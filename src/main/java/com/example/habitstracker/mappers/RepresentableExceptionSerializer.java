package com.example.habitstracker.mappers;

import com.example.habitstracker.exceptions.RepresentableException;
import com.example.openapi.dto.ErrorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RepresentableExceptionSerializer extends Mapper<RepresentableException, ErrorResponseDTO> {
    public RepresentableExceptionSerializer() {
        super(RepresentableException.class, ErrorResponseDTO.class);
    }

    @Override
    public void map(RepresentableException from, ErrorResponseDTO to) {
        to.codeError(from.getCode()).message(from.getMessage());
    }
}
