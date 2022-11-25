package com.example.habitstracker.filters;

import com.example.habitstracker.exceptions.RepresentableException;
import com.example.habitstracker.exceptions.RepresentableExceptionMapper;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр, который отлавливает возникающие исключения типа {@link RepresentableException} и отправляет ответ на клиент
 */
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final MapperService mapperService;

    @Autowired
    public ExceptionHandlerFilter(ObjectMapper objectMapper, MapperService mapperService) {
        this.objectMapper = objectMapper;
        this.mapperService = mapperService;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RepresentableException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO();
            mapperService.transform(e, errorResponse);

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}
