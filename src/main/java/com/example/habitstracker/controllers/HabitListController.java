package com.example.habitstracker.controllers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.habitstracker.security.UserCredentials;
import com.example.habitstracker.services.HabitListService;
import com.example.habitstracker.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public HabitList getHabitList() {
        UserCredentials userCredentials = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userService.getById(userCredentials.id());
        var id = user.getHabitList().getId();
        var list = habitListService.getHabitListWithId(id);
        list.getHabits().forEach(item -> item.setHabitList(null));
        // todo: возможно тут есть баг: если закрыть транзакцию затрутся данные в бд
        return list;
    }
}
