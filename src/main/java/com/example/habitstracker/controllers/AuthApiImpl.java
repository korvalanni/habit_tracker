package com.example.habitstracker.controllers;

import com.example.openapi.dto.LoginPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitstracker.services.UserService;
import com.example.openapi.api.AuthApi;
import com.example.openapi.dto.UserDTO;

/**
 * Реализация апи для аутентификации {@link AuthApi}
 */
@RestController
public class AuthApiImpl implements AuthApi
{
    private final UserService userService;

    @Autowired
    public AuthApiImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> registration(UserDTO userDTO)
    {
        // получение текущего пользователя
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> login(LoginPasswordDTO loginPasswordDTO)
    {
        // stub for swagger
        return ResponseEntity.ok().build();
    }
}