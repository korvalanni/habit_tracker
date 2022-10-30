package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для авторизации
 */
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
    @GetMapping("/test")
    public ResponseEntity<Void> testAccess() {
        return ResponseEntity.ok().build();
    }

    // http://localhost:8080/auth/registration
    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserDTO userDTO) {
        // TODO проверить, что ользователь не авторизирован *(второй раз регистрироваться нельзя)
        // получение текущего пользователя
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }
}
