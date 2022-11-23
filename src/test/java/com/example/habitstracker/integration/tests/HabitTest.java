package com.example.habitstracker.integration.tests;

import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.Color;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.Priority;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Тесты на механизмы работы с привычками
 */
class HabitTest extends AbstractIntegrationTest {
    @Autowired
    private HabitService habitService;
    @Autowired
    private MapperService mapperService;
    private Habit habit;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        super.setup();

        UserEntity user = new TestUserBuilder().build();
        habit = new Habit("Test", "Description", Priority.HIGH, Color.GREEN, 1L, List.of(1L, 2L));

        authDSL.register(user);
        authDSL.login(user);
    }

    /**
     * Проверяем, что привычка создается
     */
    @Test
    void test_createHabit() throws JsonProcessingException {
        var idDTO = habitDSL.createHabit(habit);

        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(idDTO.getId(), habits.get(0).getId());
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Test
    void test_getHabit() throws JsonProcessingException {
        habitDSL.createHabit(habit);
        var expectedHabitDTO = new HabitDTO();
        mapperService.transform(habit, expectedHabitDTO);

        HabitDTO acceptedHabitDTO = habitDSL.getHabit(String.valueOf(habit.getId()));
        Assertions.assertEquals(expectedHabitDTO, acceptedHabitDTO);
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Test
    void test_deleteHabit() throws JsonProcessingException {
        habitDSL.createHabitWithoutDelete(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        habitDSL.deleteHabit(habit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    @Test
    void test_updateHabit() throws JsonProcessingException {

        var idDTO = habitDSL.createHabit(habit);
        var id = idDTO.getId().toString();

        habit.setTitle("Test1");
        habit.setDescription("Description new");
        habit.setPriority(Priority.MIDDLE);
        habit.setDoneDates(List.of(1L, 2L));

        habitDSL.updateHabit(id, habit);

        var expectedHabitDTO = new HabitDTO();
        mapperService.transform(habit, expectedHabitDTO);

        var acceptedHabitDTO = habitDSL.getHabit(id);

        Assertions.assertEquals(expectedHabitDTO, acceptedHabitDTO);
    }
}
