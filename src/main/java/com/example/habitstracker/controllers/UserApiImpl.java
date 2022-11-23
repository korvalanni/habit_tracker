package com.example.habitstracker.controllers;

import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.security.UserCredentials;
import com.example.openapi.dto.ChangePasswordDTO;
import com.example.openapi.dto.UserWithoutPasswordDTO;
import com.example.openapi.dto.UsernameHabitListNameDTO;
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
    public ResponseEntity<UserWithoutPasswordDTO> getUserByUsername(String username)
    {
        log.info("Get user with username = " + username);

        UserWithoutPasswordDTO dto = new UserWithoutPasswordDTO();
        mapperService.transform(userService.getByUsername(username), dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> updateUserByUsername(UsernameHabitListNameDTO userDTO)
    {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        log.info("Update user " + userCredentials.username() + " with values " + userDTO.toInlineString());

        userService.updateUserById(userCredentials.id(), userDTO);

//        String username = userCredentials.username();
//        userService.updateUserByUsername(username, userDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updatePassword(ChangePasswordDTO updatePassword) {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        log.info("Update password " + credentials.username());

        userService.updatePassword(credentials.id(), updatePassword);
        // maybe u will use @PreUpdate

        return ResponseEntity.ok().build();
    }
}