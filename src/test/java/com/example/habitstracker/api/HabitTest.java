package com.example.habitstracker.api;

import com.example.habitstracker.AbstractIntegrationTest;
import com.example.habitstracker.TestUserBuilder;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.dsl.HabitDSL;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitService;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.Priority;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HabitTest extends AbstractIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HabitService habitService;
    private Habit habit;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RestAssured.port = port;
        UserEntity user = new TestUserBuilder().build();
        habit = new Habit("Test", "Description", Priority.HIGH, Color.GREEN, 0L, List.of(1L, 2L));

        AuthDSL.register(user);
        AuthDSL.login(user);
    }

    /**
     * Проверяем, что привычка создается
     */
    @Test
    void test_createHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Test
    void test_getHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        HabitDTO getHabit = HabitDSL.getHabit(String.valueOf(habit.getId()));
        Assertions.assertNotNull(getHabit);
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Disabled("Почини. Падает потому что при создании привычки добавляем задачу на ее удаление. Потом удаляем " +
            "привычку, а задание остается.")
    @Test
    void test_deleteHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        HabitDSL.deleteHabit(habit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    // todo: test for update
}
