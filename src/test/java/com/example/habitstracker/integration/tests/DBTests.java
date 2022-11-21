package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitNotFoundException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.ErrorResponseDTO;
import com.example.openapi.dto.Priority;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

public class DBTests extends AbstractIntegrationTest {
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

        Habit habit1 = new Habit();
        habit1.setHabitList(user.getHabitList());
        habit1.setTitle("H1");
        habit1.setDescription("D1");
        habit1.setColor(Color.GREEN);
        habit1.setPriority(Priority.HIGH);
        habit1.setDoneDates(List.of(1L));
        habit1.setRepeats(1L);

        Habit habit2 = new Habit();
        habit2.setHabitList(user.getHabitList());
        habit2.setTitle("H2");
        habit2.setDescription("D2");
        habit2.setColor(Color.GREEN);
        habit2.setPriority(Priority.HIGH);
        habit2.setDoneDates(List.of(1L));
        habit2.setRepeats(1L);

        habitDSL.createHabitWithoutDelete(habit1);
        habitDSL.createHabitWithoutDelete(habit2);

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

        List.of(habit1, habit2).forEach(habit -> {
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
