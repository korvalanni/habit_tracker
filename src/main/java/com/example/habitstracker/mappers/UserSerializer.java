package com.example.habitstracker.mappers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертирует {@link UserEntity} в {@link UserDTO}
 */
@Component
public class UserSerializer extends Mapper<UserEntity, UserDTO> {
    public UserSerializer() {
        super(UserEntity.class, UserDTO.class);
    }

    @Override
    public void map(UserEntity from, UserDTO to) {
        String username = from.getUsername();
        String password = from.getPassword();
        HabitList habitList = from.getHabitList();

        to.setUsername(username);
        to.setPassword(password);
        if (habitList != null)
            to.setHabitListName(habitList.getName());
    }
}
