package com.example.habitstracker.api;

import com.example.habitstracker.AbstractIntegrationTest;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

/**
 * Тесты на работу механизма валидации
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDTOValidatorTest extends AbstractIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private TypeReference<List<ErrorResponseDTO>> listType = new TypeReference<>() {
    };

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        HabitList habitList = new HabitList();
        habitList.setName("MyHabits");
        user = new User(0L, "Kennedy", "Kennedy228", habitList);
    }

    /**
     * Проверка валидации при регистрации. Все данные верны.
     */
    @Test
    void test_validationRegistration_allOk() throws JsonProcessingException {
        AuthDSL.register(user);
    }

    /**
     * Проверка валидации при регистрации. Логин слишком короткий
     */
    @Test
    void test_validationRegistration_tooShortLogin() throws JsonProcessingException {
        user.setUsername("te");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too short username")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Логин слишком длинный
     */
    @Test
    void test_validationRegistration_tooLongLogin() throws JsonProcessingException {
        user.setUsername("tetetetetetetetetetetete");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too long username")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Логин пустой
     */
    @Test
    void test_validationRegistration_emptyLogin() throws JsonProcessingException {
        user.setUsername("             ");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин null
     */
    @Test
    void test_validationRegistration_nullLogin() throws JsonProcessingException {
        user.setUsername(null);

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин содержит недопустимые символы
     */
    @Test
    void test_validationRegistration_notAllowedSymbolsInLogin() throws JsonProcessingException {
        user.setUsername("vasya#");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Field can contains only letters, digits or underscore")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин содержит как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolLogin() throws JsonProcessingException {
        user.setUsername("234234234");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("This username must contains at least one character")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Пароль слишком короткий
     */
    @Test
    void test_validationRegistration_tooShortPassword() throws JsonProcessingException {
        user.setPassword("t2");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too short password")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Пароль слишком длинный
     */
    @Test
    void test_validationRegistration_tooLongPassword() throws JsonProcessingException {
        user.setPassword("tetetete4tetetetetetetete");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too long password")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Пароль null
     */
    @Test
    void test_validationRegistration_nullPassword() throws JsonProcessingException {
        user.setPassword(null);

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("не должно равняться null")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Пароль содержит как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolPassword() throws JsonProcessingException {
        user.setPassword("234234234");

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("This password must contains at least one character")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Имя списка привычек слишком короткий
     */
    @Test
    void test_validationRegistration_tooShortHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("te"));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too short username")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Логин слишком длинный
     */
    @Test
    void test_validationRegistration_tooLongHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("tetetetetetetetetetetete"));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Too long username")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Проверка валидации при регистрации. Логин пустой
     */
    @Test
    void test_validationRegistration_emptyHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("             "));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин null
     */
    @Test
    void test_validationRegistration_nullHabitListName() throws JsonProcessingException {
        user.setUsername(null);
        user.setHabitList(new HabitList(null));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин содержит недопустимые символы
     */
    @Test
    void test_validationRegistration_notAllowedSymbolsInHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("vasya#"));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("Field can contains only letters, digits or underscore")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Проверка валидации при регистрации. Логин содержит как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("234234234"));

        var result = AuthDSL.register2(user);

        var expected = new ErrorResponseDTO()
                .message("This username must contains at least one character")
                .codeError(0);

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }
}
