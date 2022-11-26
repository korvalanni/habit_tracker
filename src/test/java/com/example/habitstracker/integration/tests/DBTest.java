package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.integration.utils.TestHabitBuilder;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

public class DBTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Проверка каскадного удаления
     * 1) Регистрируемся
     * 2) Логинимся
     * 3) Создаем две привычки
     * 4) Удаляем пользователя
     * 5) Убеждаемся, что привычки были удалены
     */
    @Test
    void test_cascadeDeleting() throws JsonProcessingException {
        UserEntity user = new TestUserBuilder().build();

        authDSL.registerWithoutCleaner(user);
        authDSL.login(user);

        List<Habit> habits = List.of(new TestHabitBuilder().build(), new TestHabitBuilder().build());
        habits.forEach(habit -> {
            try {
                habitDSL.createHabitWithoutDelete(habit);
            } catch (JsonProcessingException exception) {
                Assertions.fail();
            }
        });

        userDSL.deleteUser();

        // @formatter:off
        String json = authorized()
                .when()
                .get(ApiConstants.HabitList.GET_HABIT_LIST)
                .body()
                .asString();
        // @formatter:on

        try {
            var response = objectMapper.readValue(json, ErrorResponseDTO.class);
            Assertions.assertEquals(response.getCodeError(), ErrorCodes.USER_NOT_FOUND.getCode());
        } catch (JsonProcessingException exception) {
            Assertions.fail();
        }

        habits.forEach(habit -> {
            // @formatter:off
            String jsonBody = authorized()
                    .when()
                    .get(ApiConstants.Habit.GET_HABIT, habit.getId())
                    .body()
                    .asString();
            // @formatter:on

            try {
                var response = objectMapper.readValue(jsonBody, ErrorResponseDTO.class);
                Assertions.assertEquals(response.getCodeError(), ErrorCodes.HABIT_NOT_FOUND.getCode());
                Assertions.assertEquals(response.getMessage(), new HabitNotFoundException(habit.getId()).getMessage());
            } catch (JsonProcessingException exception) {
                Assertions.fail();
            }
        });
    }
}
