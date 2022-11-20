package com.example.habitstracker.mappers;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.LoginPasswordDTO;
import com.example.openapi.dto.ReadonlyUserDTO;
import com.example.openapi.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Сериализатор и десериализатор для {@link UserEntity}
 */
public class UserMapper {
    /**
     * Конвертирует {@link UserEntity} в {@link UserDTO}
     */
    @Component
    public static class Serializer extends Mapper<UserEntity, UserDTO> {
        public Serializer() {
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

    /**
     * Конвертирует {@link UserDTO} в {@link UserEntity}
     */
    @Component
    public static class Deserializer extends Mapper<UserDTO, UserEntity> {
        public Deserializer() {
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

    @Component
    public static class UserFromLoginPasswordDtoMapper extends Mapper<UserEntity, LoginPasswordDTO> {
        public UserFromLoginPasswordDtoMapper() {
            super(UserEntity.class, LoginPasswordDTO.class);
        }

        @Override
        public void map(UserEntity from, LoginPasswordDTO to) {
            to.setUsername(from.getUsername());
            to.setPassword(from.getPassword());
        }
    }
    /**
     * Конвертирует {@link UserEntity} в {@link ReadonlyUserDTO}
     */
    @Component
    public static class ReadonlyUserDtoMapper extends Mapper<UserEntity, ReadonlyUserDTO> {
        public ReadonlyUserDtoMapper() {
            super(UserEntity.class, ReadonlyUserDTO.class);
        }

        @Override
        public void map(UserEntity from, ReadonlyUserDTO to) {
            String username = from.getUsername();
            HabitList habitList = from.getHabitList();

            to.setUsername(username);
            if (habitList != null)
                to.setHabitListName(habitList.getName());
        }
    }
}
