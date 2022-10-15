package com.example.habitstracker.controllers;

import com.example.habitstracker.models.User;
import com.example.habitstracker.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add_user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    /*
        todo:
            Нужна авторизация пользователя. Скорее всего у каждого пользователя будет некий id или
            мб токен (зависит от реализации) т.е. идентификатор, этот идентификатор нам понадобится
            во всех запросах, которые как-то влияют на данные (создание привычки, удаление и т.п.)

            Авторизацию в library (лекция)
    */

    /*
        Todo:
            Например, один из сценариев использования сервиса:
            1) регистрируемся
            2) авторизуемся с данными, указанными при регистрации
            3) создание привычки
                Список привычек уже должен быть создан. Создается в момент регистрации.

                Для создания нужна информация о привычке (название, описание...) + userId, поэтому
                правим HabitDTO
            4) Получение списка привычек для просмотра в приложении
    */
}
