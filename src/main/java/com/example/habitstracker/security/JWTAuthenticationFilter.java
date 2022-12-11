package com.example.habitstracker.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.constants.SwaggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public JWTAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var swaggerUi = new AntPathRequestMatcher(SwaggerConstants.UI_ANY);
        var swaggerUiHtml = new AntPathRequestMatcher(SwaggerConstants.UI_HTML);
        var apiDocs = new AntPathRequestMatcher(SwaggerConstants.API_DOCS);
        if (request.getServletPath().equals(ApiConstants.Auth.REGISTRATION)
                || swaggerUi.matches(request)
                || swaggerUiHtml.matches(request)
                || apiDocs.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        var authentication = tokenAuthenticationService.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
