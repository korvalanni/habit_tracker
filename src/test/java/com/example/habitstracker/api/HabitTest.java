package com.example.habitstracker.api;

import com.example.habitstracker.TestUserBuilder;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.dsl.HabitDSL;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.User;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.utils.DatabaseUtils;
import com.example.openapi.dto.Color;
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
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HabitTest {
    @LocalServerPort
    private Integer port;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HabitService habitService;
    private User user;
    private Habit habit;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RestAssured.port = port;
        user = new TestUserBuilder().build();
        habit = new Habit("Test", "Description", Priority.HIGH, Color.GREEN, 0L, List.of(1L, 2L));

        AuthDSL.register(user);
        AuthDSL.login(user);
    }

    @BeforeEach
    void tearDown() {
        DatabaseUtils.clear(jdbcTemplate);
    }

    /**
     * Проверяем, что привычка создается
     */
    @Test
//    @Disabled
    void test_createHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Disabled("Почини")
    @Test
    void test_getHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        long id = habits.get(0).getId();

        Habit getHabit = HabitDSL.getHabit(String.valueOf(id));
        Assertions.assertNotNull(getHabit);
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Disabled("Почини")
    @Test
    void test_deleteHabit() throws JsonProcessingException {
        HabitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        long id = habits.get(0).getId();
        habit.setId(id);

        HabitDSL.deleteHabit(habit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    // todo: test for update
}
