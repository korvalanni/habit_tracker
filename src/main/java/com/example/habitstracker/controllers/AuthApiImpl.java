package com.example.habitstracker.controllers;

import com.example.habitstracker.services.UserService;
import com.example.openapi.api.AuthApi;
import com.example.openapi.dto.LoginPasswordDTO;
import com.example.openapi.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Реализация апи для аутентификации {@link AuthApi}
 */
@RestController
public class AuthApiImpl implements AuthApi
{
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public AuthApiImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> registration(UserDTO userDTO)
    {
        log.info("Registration " + userDTO.toInlineString());
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> login(LoginPasswordDTO loginPasswordDTO)
    {
        log.info("Login " + loginPasswordDTO.toInlineString());
        // stub for swagger
        return ResponseEntity.ok().build();
    }
}