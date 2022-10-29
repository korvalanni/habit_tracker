package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.models.User;
import com.example.habitstracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add_user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @GetMapping("/get_user/{nickname}")
    public User getUserByNickname(@PathVariable String nickname) {
        return userService.getByNickname(nickname);
    }

    @PostMapping("/delete_user/{nickname}")
    public void deleteUserByNickname(@PathVariable String nickname) {
        userService.deleteByNickName(nickname);
    }

    @PutMapping("/update_user/{nickname}")
    public User updateUserByNickname(@PathVariable String nickname, @RequestBody UserDTO userDTO) {
        return userService.updateUserPasswordByNickName(nickname, userDTO);
    }
}
