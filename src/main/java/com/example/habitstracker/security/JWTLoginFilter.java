package com.example.habitstracker.security;

import com.example.habitstracker.Constants;
import com.example.habitstracker.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final UserRepository userService;
    private final ObjectMapper objectMapper;
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public JWTLoginFilter(
            AuthenticationManager authenticationManager,
            UserRepository userService,
            TokenAuthenticationService tokenAuthenticationService,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(Constants.API.LOGIN));
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        var credentials = objectMapper.readValue(request.getInputStream(), AccountCredentials.class);
        var authToken = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password(), List.of());
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        tokenAuthenticationService.addAuthentication(response, authResult.getName(), userService.findByUsername(authResult.getName()).get().getUserId());
    }
}
