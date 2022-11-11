package com.example.habitstracker.mappers;

import com.example.openapi.dto.LoginPasswordDTO;
import org.springframework.stereotype.Component;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.UserDTO;

@Component
public class UserMapper {
    public static UserEntity toEntity(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String habitListName = userDTO.getHabitListName();

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setHabitList(new HabitList(habitListName));

        return user;
    }

    public static UserDTO toDTO(UserEntity user) {
        String username = user.getUsername();
        String password = user.getPassword();
        HabitList habitList = user.getHabitList();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        if (habitList != null)
            userDTO.setHabitListName(habitList.getName());

        return userDTO;
    }

    /**
     * Конвертировать пользователя в объект, в которром есть информация о логине и о пароле
     */
    public static LoginPasswordDTO toLoginPassword(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        LoginPasswordDTO loginPasswordDTO = new LoginPasswordDTO();
        loginPasswordDTO.setUsername(username);
        loginPasswordDTO.setPassword(password);

        return loginPasswordDTO;
    }
}
