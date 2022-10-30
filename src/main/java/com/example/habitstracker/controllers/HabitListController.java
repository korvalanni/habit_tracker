package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.HabitListDTO;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.services.HabitListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HabitList", description = "Control lists of habits")
@RestController
public class HabitListController {
    private final HabitListService habitListService;

    @Autowired
    public HabitListController(HabitListService habitListService) {
        this.habitListService = habitListService;
    }

    
}
