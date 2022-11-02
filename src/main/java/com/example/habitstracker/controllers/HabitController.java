package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.HabitDTO;
import com.example.habitstracker.dto.IdDTO;
import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/*
    Контроллер для привычки
*/
@Tag(name = "Habit", description = "Control habits")
@RestController
@RequestMapping("/habit")
public class HabitController {
    private final HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @Operation(summary = "Create new habit")
    @PostMapping("/create_habit")
    public ResponseEntity<Void> createHabit(@RequestBody HabitDTO habitDTO) {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Habit habit = HabitMapper.toEntity(habitDTO);
        habitService.addHabit(credentials, habit);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get habit by id")
    @GetMapping("/get_habit/{id}")
    public Habit getHabit(@PathVariable long id) {
        return habitService.getHabit(id);
    }

    @Operation(summary = "Delete habit by id")
    @DeleteMapping("/delete_habit")
    public void deleteHabit(@RequestBody IdDTO idDTO) {
        UserCredentials credentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        habitService.deleteHabit(idDTO.id());
    }

    @PutMapping("/update_habit/{id}")
    public void updateHabit(@PathVariable long id, @RequestBody HabitDTO habitDTO) {
        Habit habit = HabitMapper.toEntity(habitDTO);
        habitService.updateHabit(id, habit);
    }
}
