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
    @GetMapping("/get_user/{nickname}")
    public User getUserByNickname(@PathVariable String nickname) {
        return userService.getByNickname(nickname);
    }

    @Operation(summary = "Delete user")
    @PostMapping("/delete_user/{nickname}")
    public void deleteUserByNickname(@PathVariable String nickname) {
        userService.deleteByNickName(nickname);
    }

    @Operation(summary = "Update user")
    @PutMapping("/update_user/{nickname}")
    public User updateUserByNickname(@PathVariable String nickname, @RequestBody UserDTO userDTO) {
        return userService.updateUserPasswordByNickName(nickname, userDTO);
    }
}
