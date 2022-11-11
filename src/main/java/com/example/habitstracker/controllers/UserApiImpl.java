package com.example.habitstracker.controllers;

import com.example.habitstracker.security.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.services.UserService;
import com.example.openapi.api.UserApi;
import com.example.openapi.dto.UserDTO;

/**
 *
 */
@RestController
public class UserApiImpl implements UserApi
{
    private final UserService userService;

    @Autowired
    public UserApiImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> deleteUserByUsername(String username)
    {
        userService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<UserDTO> getUserByUsername(String username)
    {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getByUsername(username)));
    }

    @Override
    public ResponseEntity<Void> updateUserByUsername(UserDTO userDTO)
    {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String username = userCredentials.username();
        userService.updateUserByUsername(username, userDTO); 
        return ResponseEntity.ok().build();
    }
}