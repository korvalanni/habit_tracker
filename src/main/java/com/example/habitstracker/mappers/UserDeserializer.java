package com.example.habitstracker.mappers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертирует {@link UserDTO} в {@link UserEntity}
 */
@Component
public class UserDeserializer extends Mapper<UserDTO, UserEntity> {
    public UserDeserializer() {
        super(UserDTO.class, UserEntity.class);
    }

    @Override
    public void map(UserDTO from, UserEntity to) {
        String username = from.getUsername();
        String password = from.getPassword();
        String habitListName = from.getHabitListName();

        to.setUsername(username);
        to.setPassword(password);
        to.setHabitList(new HabitList(habitListName));
    }
}
