package com.example.habitstracker.controllers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.HabitListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
    Контроллер для листа привычек
 */
@RestController
public class HabitListController {
    private final HabitListService habitListService;

    @Autowired
    public HabitListController(HabitListService habitListService) {
        this.habitListService = habitListService;
    }

    @GetMapping("/get_habitList/{id}")
    public HabitList getHabitList(@PathVariable Long id) {
        return habitListService.getHabitListWithId(id);
    }


}
