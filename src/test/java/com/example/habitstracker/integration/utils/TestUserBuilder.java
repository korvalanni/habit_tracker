package com.example.habitstracker.integration.utils;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.UserDTO;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.UUID;

/**
 * Класс для создания пользователя со случайными данными. Использовать только для тестов системы.
 */
public class TestUserBuilder {
    private long id = 0;
    private String username = generateUniqueString();
    private String password = generateUniqueString();
    private HabitList habitList = new HabitList(generateUniqueString());

    private String generateUniqueString() {
        return UUID
                .randomUUID()
                .toString()
                .substring(0, 18) // Длина имени должна быть не более 20 символов
                .replace("-", "_"); // Нельзя использовать в имени дефисы
    }

    public TestUserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TestUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public TestUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public TestUserBuilder setHabitList(HabitList habitList) {
        this.habitList = habitList;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(id, username, password, habitList);
    }
}
