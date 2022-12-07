package com.example.habitstracker.controllers;

import com.example.habitstracker.exceptions.HabitPermissionException;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.services.MapperService;
import com.example.habitstracker.services.UserService;
import com.example.openapi.api.HabitApi;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.IdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Контроллер для привычки
 */
@RestController
public class HabitApiImpl implements HabitApi
{
    private final HabitService habitService;
    private final MapperService mapperService;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public HabitApiImpl(HabitService habitService, MapperService mapperService, UserService userService)
    {
        this.habitService = habitService;
        this.mapperService = mapperService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<IdDTO> createHabit(HabitDTO habitDTO)
    {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        log.info("Create " + habitDTO.toInlineString());

        Habit habit = new Habit();
        mapperService.transform(habitDTO, habit);
        habitService.addHabit(credentials, habit);
        return ResponseEntity.ok(new IdDTO().id(habit.getId()));
    }

    @Override
    public ResponseEntity<Void> deleteHabit(Long id)
    {
        log.info("Delete habit " + id);

        if(isNotUserHabit(id))
            throw new HabitPermissionException(id);


        habitService.deleteHabit(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HabitDTO> getHabit(Long id)
    {
        log.info("Get habit with id = " + id);

        if(isNotUserHabit(id)){
            System.out.println("я сгенерил исключение");
            throw new HabitPermissionException(id);}

        HabitDTO habitDTO = new HabitDTO();
        mapperService.transform(habitService.getHabit(id), habitDTO);
        return ResponseEntity.ok(habitDTO);
    }

    @Override
    public ResponseEntity<Void> updateHabit(Long id, HabitDTO habitDTO)
    {
        log.info("Update habit with id = " + id + " new values " + habitDTO.toInlineString());
        if(isNotUserHabit(id))
            throw new HabitPermissionException(id);

        Habit habit = new Habit();
        mapperService.transform(habitDTO, habit);
        habitService.updateHabit(id, habit);
        return ResponseEntity.ok().build();
    }

    private boolean isNotUserHabit(Long id){
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        UserEntity user = userService.getById(userCredentials.id());
        var habitListRegisteredId = user.getHabitList().getId();
        var habitHabitListId = habitService.getHabit(id).getHabitList().getId();

        return !habitHabitListId.equals(habitListRegisteredId);
    }
}