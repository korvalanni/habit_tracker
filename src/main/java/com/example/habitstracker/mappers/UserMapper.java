package com.example.habitstracker.mappers;

import com.example.habitstracker.controllers.UserDTO;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        Long id = userDTO.getUserId();

        String nickname = userDTO.getNickname();

        String password = userDTO.getPassword();

        HabitList habitList = userDTO.getHabitList();

        User user = new User();
        user.setUserId(id);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setHabitList(habitList);

        return user;
    }

    public static UserDTO toDTO(User user) {
        Long id = user.getUserId();

        String nickname = user.getNickname();

        String password = user.getPassword();

        HabitList habitList = user.getHabitList();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        userDTO.setNickname(nickname);
        userDTO.setPassword(password);
        userDTO.setHabitList(habitList);

        return userDTO;
    }
}
