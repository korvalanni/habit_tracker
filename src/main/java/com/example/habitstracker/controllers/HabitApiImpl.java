package com.example.habitstracker.controllers;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.api.HabitApi;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.IdDTO;
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
public class HabitApiImpl implements HabitApi
{
    private final HabitService habitService;
    private final MapperService mapperService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public HabitApiImpl(HabitService habitService, MapperService mapperService)
    {
        this.habitService = habitService;
        this.mapperService = mapperService;
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
    public ResponseEntity<Void> deleteHabit(IdDTO idDTO)
    {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        log.info("Delete habit " + idDTO.toInlineString());

        habitService.deleteHabit(idDTO.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HabitDTO> getHabit(Long id)
    {
        log.info("Delete habit with id = " + id);

        HabitDTO habitDTO = new HabitDTO();
        mapperService.transform(habitService.getHabit(id), habitDTO);
        return ResponseEntity.ok(habitDTO);
    }

    @Override
    public ResponseEntity<Void> updateHabit(Long id, HabitDTO habitDTO)
    {
        log.info("Update habit with id = " + id + " new values " + habitDTO.toInlineString());

        Habit habit = new Habit();
        mapperService.transform(habitDTO, habit);
        habitService.updateHabit(id, habit);
        return ResponseEntity.ok().build();
    }
}