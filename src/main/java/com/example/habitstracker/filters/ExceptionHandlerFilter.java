package com.example.habitstracker.filters;

import com.example.habitstracker.exceptions.RepresentableException;
import com.example.habitstracker.exceptions.RepresentableExceptionMapper;
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
    private final RepresentableExceptionMapper exceptionMapper;

    @Autowired
    public ExceptionHandlerFilter(ObjectMapper objectMapper, RepresentableExceptionMapper exceptionMapper) {
        this.objectMapper = objectMapper;
        this.exceptionMapper = exceptionMapper;
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
            var errorResponse = exceptionMapper.toDTO(e);

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}
