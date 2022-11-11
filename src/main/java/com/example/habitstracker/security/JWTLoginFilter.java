package com.example.habitstracker.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.auth.IncorrectCredentialsException;
import com.example.habitstracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public JWTLoginFilter(
            AuthenticationManager authenticationManager,
            UserService userService,
            TokenAuthenticationService tokenAuthenticationService,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(ApiConstants.LOGIN));
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {
        var credentials = objectMapper.readValue(request.getInputStream(), AccountCredentials.class);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credentials.username(),
                credentials.password(),
                List.of());

        return tryAuthenticate(authToken);
    }

    private Authentication tryAuthenticate(UsernamePasswordAuthenticationToken authToken) {
        try {
            return getAuthenticationManager().authenticate(authToken);
        } catch (BadCredentialsException exception) {
            throw new IncorrectCredentialsException();
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        String username = authResult.getName();
        long userId = userService.getByUsername(username).getUserId();
        tokenAuthenticationService.addAuthentication(response, username, userId);
    }
}
