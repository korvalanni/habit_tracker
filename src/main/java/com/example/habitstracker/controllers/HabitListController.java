package com.example.habitstracker.controllers;

import com.example.habitstracker.dto.HabitListDTO;
import com.example.habitstracker.mappers.HabitListMapper;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
    Контроллер для листа привычек
 */
@Tag(name = "HabitList", description = "Control lists of habits")
@RestController
@RequestMapping("/habit_list")
public class HabitListController {
    private final HabitListService habitListService;
    private final UserService userService;


    @Autowired
    public HabitListController(HabitListService habitListService, UserService userService) {
        this.habitListService = habitListService;
        this.userService = userService;
    }

    @GetMapping("/get_habitList")
    public HabitListDTO getHabitList() {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userService.getById(userCredentials.id());
        var id = user.getHabitList().getId();
        HabitList habitList = habitListService.getHabitListWithId(id);
        return HabitListMapper.listDTO(habitList);
    }
}
