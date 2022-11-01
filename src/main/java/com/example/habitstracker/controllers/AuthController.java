package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для авторизации
 */
@Tag(name = "Auth", description = "Login and registration. Get JWT.")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register new account")
    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserDTO userDTO) {
        // TODO проверить, что ользователь не авторизирован *(второй раз регистрироваться нельзя)
        // получение текущего пользователя
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Login in system and get JWT")
    @PostMapping("/login")
    public void login(@RequestBody UserDTO userDTO) {
        // stub for swagger
    }
}
