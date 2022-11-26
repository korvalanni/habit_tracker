package com.example.habitstracker.controllers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.api.HabitListApi;
import com.example.openapi.dto.HabitListDTO;
import com.example.openapi.dto.NameDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public HabitListApiImpl(HabitListService habitListService, UserService userService, MapperService mapperService) {
        this.habitListService = habitListService;
        this.userService = userService;
        this.mapperService = mapperService;
    }

    @Override
    public ResponseEntity<HabitListDTO> getHabitList() {
        log.info("Get habit list");

        var id = getAuthUserHabitList().getId();
        HabitList habitList = habitListService.getHabitListWithId(id);

        HabitListDTO habitListDTO = new HabitListDTO();
        mapperService.transform(habitList, habitListDTO);

        return ResponseEntity.ok(habitListDTO);
    }

    @Override
    public ResponseEntity<Void> updateHabitList(NameDTO nameDTO) {

        var name = nameDTO.getName();

        log.info(String.format("update habit list name to %s", name));

        var id = getAuthUserHabitList().getId();

        habitListService.updateHabitListName(id, name);

        return ResponseEntity.ok().build();

    }

    private HabitList getAuthUserHabitList() {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().
                getAuthentication().getCredentials();
        UserEntity user = userService.getById(userCredentials.id());

        return user.getHabitList();
    }
}