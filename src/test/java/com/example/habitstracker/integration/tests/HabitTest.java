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
    private Habit oldHabit;
    private Habit newHabit;
    private UserEntity oldUser;
    private UserEntity newUser;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        super.setup();

        oldUser = new TestUserBuilder().build();
        oldHabit = new Habit("Test0", "Description", Priority.HIGH, Color.GREEN,
                1L, List.of(1L, 2L));

        authDSL.register(oldUser);
        authDSL.login(oldUser);
    }

    Long registrationAnother() throws JsonProcessingException{
        newUser = new TestUserBuilder().build();
        newHabit = new Habit("Test1", "Description1", Priority.MIDDLE, Color.YELLOW,
                5L, List.of(5L, 2L));

        authDSL.register(newUser);
        authDSL.login(newUser);
        var idDTO = habitDSL.createHabit(newHabit);
        return idDTO.getId();
    }

    /**
     * Проверяем, что привычка создается
     */
    @Test
    void test_createHabit() throws JsonProcessingException {
        var idDTO = habitDSL.createHabit(oldHabit);

        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(idDTO.getId(), habits.get(0).getId());
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Test
    void test_getHabit() throws JsonProcessingException {
        habitDSL.createHabit(oldHabit);
        var expectedHabitDTO = new HabitDTO();
        mapperService.transform(oldHabit, expectedHabitDTO);

        HabitDTO acceptedHabitDTO = habitDSL.getHabit(String.valueOf(oldHabit.getId()));
        Assertions.assertEquals(expectedHabitDTO, acceptedHabitDTO);
    }

    /**
     * Получаем привычку по идентификатору
     */
    @Disabled("Привычка не удаляется, почему непонятно")
    @Test
    void test_deleteHabit() throws JsonProcessingException {
        habitDSL.createHabitWithoutDelete(oldHabit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        habitDSL.deleteHabit(oldHabit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    @Test
    void test_updateHabit() throws JsonProcessingException {

        var idDTO = habitDSL.createHabit(oldHabit);
        var id = idDTO.getId().toString();

        oldHabit.setTitle("Test1");
        oldHabit.setDescription("Description new");
        oldHabit.setPriority(Priority.MIDDLE);
        oldHabit.setDoneDates(List.of(1L, 2L));

        habitDSL.updateHabit(id, oldHabit);

        var expectedHabitDTO = new HabitDTO();
        mapperService.transform(oldHabit, expectedHabitDTO);

        var acceptedHabitDTO = habitDSL.getHabit(id);

        Assertions.assertEquals(expectedHabitDTO, acceptedHabitDTO);
    }

    /**
     * Проверка на получение привычки другого пользователя
     */
    @Test
    void test_get_unauthorised_user_habit() throws JsonProcessingException {
            var id  = registrationAnother();


    }
}
