package com.example.habitstracker.api;

import com.example.habitstracker.AbstractIntegrationTest;
import com.example.habitstracker.TestUserBuilder;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.dsl.HabitDSL;
import com.example.habitstracker.mappers.HabitMapper;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.User;
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
    private HabitService habitService;
    private Habit habit;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RestAssured.port = port;
        User user = new TestUserBuilder().build();
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

    @Test
    void test_updateHabit() throws JsonProcessingException{
        HabitDSL.createHabit(habit);
        String id = habit.getId().toString();
        Habit habit1 = new Habit();
        habit1.setTitle("Test1");
        habit1.setDescription("Description new");
        habit1.setPriority(Priority.MIDDLE);
        HabitDSL.updateHabit(id, habit1);
        Habit expectedHabit = new Habit("Test1", "Description new",
                Priority.MIDDLE, Color.GREEN, 0L, List.of(1L, 2L));
        Habit gotHabit = HabitMapper.toEntity(HabitDSL.getHabit(id));
        Assertions.assertEquals(expectedHabit.getTitle(), gotHabit.getTitle());
        Assertions.assertEquals(expectedHabit.getDescription(), gotHabit.getDescription());
        Assertions.assertEquals(expectedHabit.getPriority(), gotHabit.getPriority());
        Assertions.assertEquals(expectedHabit.getColor(), gotHabit.getColor());
        Assertions.assertEquals(expectedHabit.getRepeats(), gotHabit.getRepeats());
        Assertions.assertEquals(expectedHabit.getDoneDates(), gotHabit.getDoneDates());
    }
}
