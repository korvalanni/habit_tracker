package com.example.habitstracker.controllers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.api.HabitListApi;
import com.example.openapi.dto.HabitListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для привычки
 */
@RestController
public class HabitListApiImpl implements HabitListApi {
    private final HabitListService habitListService;
    private final UserService userService;
    private final MapperService mapperService;

    @Autowired
    public HabitListApiImpl(HabitListService habitListService, UserService userService, MapperService mapperService) {
        this.habitListService = habitListService;
        this.userService = userService;
        this.mapperService = mapperService;
    }

    @Override
    public ResponseEntity<HabitListDTO> getHabitList() {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        UserEntity user = userService.getById(userCredentials.id());
        var id = user.getHabitList().getId();
        HabitList habitList = habitListService.getHabitListWithId(id);

        HabitListDTO habitListDTO = new HabitListDTO();
        mapperService.transform(habitList, habitListDTO);

        return ResponseEntity.ok(habitListDTO);
    }

    @Override
    public ResponseEntity<Void> updateHabitList(Long id, HabitListDTO habitListDTO) {
        String newHabitListName = habitListDTO.getName();
        habitListService.updateListName(id, newHabitListName);
        return ResponseEntity.ok().build();
    }
}