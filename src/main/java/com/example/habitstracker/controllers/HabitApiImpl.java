package com.example.habitstracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitService;
import com.example.openapi.api.HabitApi;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.IdDTO;

/**
 * Контроллер для привычки
 */
@RestController
public class HabitApiImpl implements HabitApi
{
    private final HabitService habitService;

    @Autowired
    public HabitApiImpl(HabitService habitService)
    {
        this.habitService = habitService;
    }

    @Override
    public ResponseEntity<IdDTO> createHabit(HabitDTO habitDTO)
    {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Habit habit = HabitMapper.toEntity(habitDTO);
        habitService.addHabit(credentials, habit);
        return ResponseEntity.ok(new IdDTO().id(habit.getId()));
    }

    @Override
    public ResponseEntity<Void> deleteHabit(IdDTO idDTO)
    {
        // FIXME Write javadoc
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        habitService.deleteHabit(idDTO.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HabitDTO> getHabit(Long id)
    {
        return ResponseEntity.ok(HabitMapper.toDTO(habitService.getHabit(id)));
    }

    @Override
    public ResponseEntity<Void> updateHabit(Long id, HabitDTO habitDTO)
    {
        Habit habit = HabitMapper.toEntity(habitDTO);
        habitService.updateHabit(id, habit);
        return ResponseEntity.ok().build();
    }
}