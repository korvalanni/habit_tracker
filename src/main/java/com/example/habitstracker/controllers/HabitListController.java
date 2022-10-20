package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.HabitListDTO;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.HabitListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitListController {
    private final HabitListService habitListService;

    @Autowired
    public HabitListController(HabitListService habitListService) {
        this.habitListService = habitListService;
    }

    @PostMapping("/habit_list")
    public HabitList createHabitList(@RequestBody HabitListDTO habitListDTO) {
        return habitListService.addHabitList(habitListDTO);
    }
}
