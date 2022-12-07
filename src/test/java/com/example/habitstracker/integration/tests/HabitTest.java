package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.HabitPermissionException;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.HabitService;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.json.JsonOutput;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;
import static io.restassured.RestAssured.given;

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
        newHabit = new Habit("Test1", "Description1", Priority.MIDDLE, Color.YELLOW,
                5L, List.of(5L, 2L));
        oldHabit = new Habit("Test0", "Description", Priority.HIGH, Color.GREEN,
                1L, List.of(1L, 2L));
        authDSL.register(oldUser);
        authDSL.register(newUser);

    }

    private ErrorResponseDTO getExpected(Long id){
        var exception = new HabitPermissionException(id);
        var expected = new ErrorResponseDTO()
                .codeError(ErrorCodes.HABIT_PERMISSION_EXCEPTION.getCode())
                .message(exception.getMessage());
        return expected;
    }

    private IdDTO changeAuthUser() throws JsonProcessingException {
        authDSL.login(oldUser);
        var oldHabitIdDTO = habitDSL.createHabit(oldHabit);

        authDSL.login(newUser);
        habitDSL.createHabit(newHabit);

        return oldHabitIdDTO;
    }


    /**
     * Проверяем, что привычка создается
     */
    @Test
    void test_createHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
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
        authDSL.login(oldUser);
        habitDSL.createHabit(oldHabit);
        var expectedHabitDTO = new HabitDTO();
        mapperService.transform(oldHabit, expectedHabitDTO);

        HabitDTO acceptedHabitDTO = habitDSL.getHabit(String.valueOf(oldHabit.getId()));
        Assertions.assertEquals(expectedHabitDTO, acceptedHabitDTO);
    }

    /**
     * Получаем привычку по идентификатору
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

    @Test
    void test_updateHabit() throws JsonProcessingException {
        authDSL.login(oldUser);
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
            var oldHabitIdDTO = changeAuthUser();
            var oldHabitId = oldHabitIdDTO.getId();

            // @formatter:off
            var response = authorized()
                                        .contentType(ContentType.JSON)
                                        .body(objectMapper.writeValueAsString(oldHabitIdDTO))
                                  .when()
                                         .get(ApiConstants.Habit.GET_HABIT, oldHabitId.toString())
                                         .getBody()
                                         .asString();
            // @formatter:on
            System.out.println(response);
            //{"timestamp":1670207908825,"status":500,"error":"Internal Server Error","path":"/habit/get_habit/1"}
            //надо {"codeError":"HABIT_PERMISSION_EXCEPTION","message":"Non-authorized user has habit with id %s"}
            var result = objectMapper.readValue(response, HabitPermissionException.class);

            var expected = getExpected(oldHabitId);
            Assertions.assertEquals(expected, result);
    }

    /**
     * Проверка на обновление привычки другого пользователя
     */
    @Test
    void test_update_unauthorised_user_habit() throws JsonProcessingException {
            var oldHabitIdDTO = changeAuthUser();
            var oldHabitId = oldHabitIdDTO.getId();
            var updatedHabit = new Habit("Updated", "Description", Priority.HIGH, Color.GREEN,
                    1L, List.of(1L, 2L));
            var habitDTO = new HabitDTO();
            mapperService.transform(updatedHabit, habitDTO);

            // @formatter:off
            var response = authorized()
                                              .contentType(ContentType.JSON)
                                              .body(objectMapper.writeValueAsString(habitDTO))
                                 .when()
                                              .put(ApiConstants.Habit.UPDATE_HABIT, oldHabitId)
                                              .getBody()
                                              .asString();
            // @formatter:on
            System.out.println(response);
            var result = objectMapper.readValue(response, HabitPermissionException.class);

            var expected = getExpected(oldHabitId);
            Assertions.assertEquals(expected, result);
    }


    /**
     * Проверка на удаление привычки другого пользователя
     */
    @Test
    void test_delete_unauthorised_user_habit() throws JsonProcessingException {
            var oldHabitIdDTO = changeAuthUser();
            var oldHabitId = oldHabitIdDTO.getId();

            // @formatter:off
            var response = authorized()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(oldHabitIdDTO))
                    .when()
                    .delete(ApiConstants.Habit.DELETE_HABIT, oldHabitId.toString())
                    .getBody()
                    .asString();
            // @formatter:on
            System.out.println(response);
            //{"timestamp":1670211883671,"status":500,"error":"Internal Server Error","path":"/habit/delete_habit/1"}
            //надо {"codeError":"HABIT_PERMISSION_EXCEPTION","message":"Non-authorized user has habit with id %s"}

            var result = objectMapper.readValue(response, HabitPermissionException.class);

            var expected = getExpected(oldHabitId);
            Assertions.assertEquals(expected, result);
    }
}
