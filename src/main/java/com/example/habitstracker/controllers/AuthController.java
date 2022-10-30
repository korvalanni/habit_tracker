package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Тестовый метод. Должен быть недоступен без авторизации
     */
    @Operation(summary = "Method for test access")
    @GetMapping("/test")
    public ResponseEntity<Void> testAccess() {
        return ResponseEntity.ok().build();
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
