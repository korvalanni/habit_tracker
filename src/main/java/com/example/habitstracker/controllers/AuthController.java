package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для авторизации
 */
@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Тестовый метод. Должен быть недоступен без авторизации
     */
    @GetMapping("/test")
    public ResponseEntity<Void> testAccess() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }
}
