package com.example.habitstracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitstracker.mappers.HabitListMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.api.HabitListApi;
import com.example.openapi.dto.HabitListDTO;

/**
 * Контроллер для привычки
 */
@RestController
public class HabitListApiImpl implements HabitListApi
{
    private final HabitListService habitListService;
    private final UserService userService;

    @Autowired
    public HabitListApiImpl(HabitListService habitListService, UserService userService)
    {
        this.habitListService = habitListService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<HabitListDTO> getHabitList() {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userService.getById(userCredentials.id());
        var id = user.getHabitList().getId();
        HabitList habitList = habitListService.getHabitListWithId(id);
        return ResponseEntity.ok(HabitListMapper.listDTO(habitList));
    }
}