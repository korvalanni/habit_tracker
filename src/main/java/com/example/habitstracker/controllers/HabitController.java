package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.HabitDTO;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.services.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitController {
    private final HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }


    @PostMapping("/habit")
    public Habit createHabit(@RequestBody HabitDTO habitDTO) {
        return habitService.addHabit(habitDTO);
    }

    // todo: get habit
    // todo: delete habit
    // todo: update habit
}
