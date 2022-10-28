package com.example.habitstracker.mappers;

import com.example.habitstracker.dto.UserDTO;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        String nickname = userDTO.getNickname();

        String password = userDTO.getPassword();

        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setHabitList(new HabitList(userDTO.getHabitListName()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        Long id = user.getUserId();

        String nickname = user.getNickname();

        String password = user.getPassword();


        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(nickname);
        userDTO.setPassword(password);

        return userDTO;
    }
}
