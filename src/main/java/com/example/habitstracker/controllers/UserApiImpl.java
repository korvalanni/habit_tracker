package com.example.habitstracker.controllers;

import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.security.UserCredentials;
import com.example.openapi.dto.ReadonlyUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

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
    private final MapperService mapperService;
    private final Logger log = LoggerFactory.getLogger(UserApiImpl.class);

    @Autowired
    public UserApiImpl(UserService userService, MapperService mapperService)
    {
        this.userService = userService;
        this.mapperService = mapperService;
    }

    @Override
    public ResponseEntity<Void> deleteUserByUsername()
    {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        log.info("Delete user with username = " + userCredentials.username());

        userService.deleteByUsername(userCredentials.username());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ReadonlyUserDTO> getUserByUsername(String username)
    {
        log.info("Get user with username = " + username);

        ReadonlyUserDTO dto = new ReadonlyUserDTO();
        mapperService.transform(userService.getByUsername(username), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> updateUserByUsername(UserDTO userDTO)
    {
        log.info("Update " + userDTO.toInlineString());

        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String username = userCredentials.username();
        userService.updateUserByUsername(username, userDTO);
        return ResponseEntity.ok().build();
    }
}