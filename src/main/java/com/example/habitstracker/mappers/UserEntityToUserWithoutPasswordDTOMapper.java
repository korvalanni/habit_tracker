package com.example.habitstracker.mappers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.UserWithoutPasswordDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертирует {@link UserEntity} в {@link UserWithoutPasswordDTO}
 */
@Component
public class UserEntityToUserWithoutPasswordDTOMapper extends Mapper<UserEntity, UserWithoutPasswordDTO> {
    public UserEntityToUserWithoutPasswordDTOMapper() {
        super(UserEntity.class, UserWithoutPasswordDTO.class);
    }

    @Override
    public void map(UserEntity from, UserWithoutPasswordDTO to) {
        String username = from.getUsername();
        HabitList habitList = from.getHabitList();

        to.setUsername(username);
        if (habitList != null)
            to.setHabitListName(habitList.getName());
    }
}
