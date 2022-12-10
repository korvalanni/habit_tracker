package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitPermissionException;
import com.example.habitstracker.integration.utils.TestHabitBuilder;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;

/**
 * Тесты на механизмы работы с привычками
 */
class HabitTest extends AbstractIntegrationTest {
    @Autowired
    private HabitService habitService;
    @Autowired
    private MapperService mapperService;
    @Autowired
    private ObjectMapper objectMapper;
    private Habit oldHabit;
    private Habit newHabit;
    private UserEntity oldUser;
    private UserEntity newUser;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        super.setup();

        oldUser = new TestUserBuilder().build();
        newUser = new TestUserBuilder().build();
        newHabit = new TestHabitBuilder().build();
        oldHabit = new TestHabitBuilder().build();
        authDSL.register(oldUser);
        authDSL.register(newUser);

    }

    private ErrorResponseDTO getExpected(Long id) {
        var exception = new HabitPermissionException(id);
        var expected = new ErrorResponseDTO()
                .codeError(ErrorCodes.HABIT_PERMISSION_EXCEPTION.getCode())
                .message(exception.getMessage());
        return expected;
    }


    private void createLoginUserHabit(UserEntity user, Habit habit) throws JsonProcessingException {
        authDSL.login(user);
        habitDSL.createHabit(habit);
    }


    /**
     * Проверяем, что привычка создается у зарегистрированного пользователя
     */
    @Test
    void test_createHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
        IdDTO idDTO = habitDSL.createHabit(oldHabit);

        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(idDTO.getId(), habits.get(0).getId());
    }

    /**
     * Получаем привычку по идентификатору у зарегистрированного пользователя
     */
    @Test
    void test_getHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
        habitDSL.createHabit(oldHabit);
        HabitDTO expected = new HabitDTO();
        mapperService.transform(oldHabit, expected);

        HabitDTO result = habitDSL.getHabit(String.valueOf(oldHabit.getId()));
        Assertions.assertEquals(expected, result);
    }

    /**
     * Удаляем привычку у зарегистрированного пользователя
     */
    @Test
    void test_deleteHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
        habitDSL.createHabitWithoutDelete(oldHabit);
        List<Habit> habits = habitService.getHabits();

        Assertions.assertEquals(1, habits.size());

        habitDSL.deleteHabit(oldHabit);
        habits = habitService.getHabits();

        Assertions.assertEquals(0, habits.size());
    }

    /**
     * Обновляем привычку у зарегистрированного пользователя
     */
    @Test
    void test_updateHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
        IdDTO idDTO = habitDSL.createHabit(oldHabit);
        String id = idDTO.getId().toString();

        oldHabit.setTitle("Test1");
        oldHabit.setDescription("Description new");
        oldHabit.setPriority(Priority.MIDDLE);
        oldHabit.setDoneDates(List.of(1L, 2L));

        habitDSL.updateHabit(id, oldHabit);

        HabitDTO expected = new HabitDTO();
        mapperService.transform(oldHabit, expected);

        HabitDTO result = habitDSL.getHabit(id);

        Assertions.assertEquals(expected, result);
    }

    /**
     * Проверка на получение привычки другого пользователя
     */
    @Test
    void test_get_unauthorised_user_habit() throws JsonProcessingException {
        createLoginUserHabit(oldUser, oldHabit);
        Long oldHabitId = oldHabit.getId();
        createLoginUserHabit(newUser, newHabit);

        //@formatter:off
        String response = authorized()
                .when()
                        .get(ApiConstants.Habit.GET_HABIT, oldHabitId.toString())
                        .getBody()
                        .asString();
       //@formatter:on
        ErrorResponseDTO result = objectMapper.readValue(response, ErrorResponseDTO.class);

        ErrorResponseDTO expected = getExpected(oldHabitId);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Проверка на обновление привычки другого пользователя
     */
    @Test
    void test_update_unauthorised_user_habit() throws JsonProcessingException {
        createLoginUserHabit(oldUser, oldHabit);
        Long oldHabitId = oldHabit.getId();
        createLoginUserHabit(newUser, newHabit);

        Habit updatedHabit = new TestHabitBuilder().build();
        HabitDTO updatedHabitDTO = new HabitDTO();
        mapperService.transform(updatedHabit, updatedHabitDTO);

        // @formatter:off
        String response = authorized()
                                              .contentType(ContentType.JSON)
                                              .body(objectMapper.writeValueAsString(updatedHabitDTO))
                                 .when()
                                              .put(ApiConstants.Habit.UPDATE_HABIT, oldHabitId)
                                              .getBody()
                                              .asString();
        // @formatter:on

        ErrorResponseDTO result = objectMapper.readValue(response, ErrorResponseDTO.class);

        ErrorResponseDTO expected = getExpected(oldHabitId);
        Assertions.assertEquals(expected, result);
    }


    /**
     * Проверка на удаление привычки другого пользователя
     */
    @Test
    void test_delete_unauthorised_user_habit() throws JsonProcessingException {
        createLoginUserHabit(oldUser, oldHabit);
        Long oldHabitId = oldHabit.getId();
        createLoginUserHabit(newUser, newHabit);

        //@formatter:off
        String response = authorized()
                .when()
                        .delete(ApiConstants.Habit.DELETE_HABIT, oldHabitId.toString())
                        .getBody()
                        .asString();
        //@formatter:on

        ErrorResponseDTO result = objectMapper.readValue(response, ErrorResponseDTO.class);

        ErrorResponseDTO expected = getExpected(oldHabitId);
        Assertions.assertEquals(expected, result);
    }
}
