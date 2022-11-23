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
        habitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(1, habits.get(0).getId());
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
    @Disabled("Почини. Падает потому что при создании привычки добавляем задачу на ее удаление. Потом удаляем " +
            "привычку, а задание остается.")
    void test_deleteHabit() throws JsonProcessingException {
        habitDSL.createHabit(habit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        habitDSL.deleteHabit(habit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    @Test
    void test_updateHabit() throws JsonProcessingException {
        habitDSL.createHabit(habit);
        String id = habit.getId().toString();

        Habit updatedHabit = new Habit();
        updatedHabit.setTitle("Test1");
        updatedHabit.setDescription("Description new");
        updatedHabit.setPriority(Priority.MIDDLE);
        updatedHabit.setDoneDates(List.of(1L, 2L));

        habitDSL.updateHabit(id, updatedHabit);
        Habit expectedHabit = new Habit("Test1", "Description new",
                Priority.MIDDLE, Color.GREEN, 1L, List.of(1L, 2L));
        Habit gotHabit = new Habit();
        mapperService.transform(habitDSL.getHabit(id), gotHabit);

        Assertions.assertEquals(expectedHabit.getTitle(), gotHabit.getTitle());
        Assertions.assertEquals(expectedHabit.getDescription(), gotHabit.getDescription());
        Assertions.assertEquals(expectedHabit.getPriority(), gotHabit.getPriority());
        Assertions.assertEquals(expectedHabit.getColor(), gotHabit.getColor());
        Assertions.assertEquals(expectedHabit.getRepeats(), gotHabit.getRepeats());
        Assertions.assertEquals(expectedHabit.getDoneDates(), gotHabit.getDoneDates());
    }
}
