package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.models.User;
import com.example.habitstracker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "Control users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new user")
    @PostMapping("/add_user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @Operation(summary = "Get user")
    @GetMapping("/get_user/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @Operation(summary = "Delete user")
    @PostMapping("/delete_user/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteByUsername(username);
    }

    @Operation(summary = "Update user")
    @PutMapping("/update_user/{username}")
    public User updateUserByUsername(@PathVariable String username, @RequestBody UserDTO userDTO) {
        return userService.updateUserPasswordByUsername(username, userDTO);
    }
}
