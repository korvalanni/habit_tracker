package com.example.habitstracker.api;

import com.example.habitstracker.AbstractIntegrationTest;
import com.example.habitstracker.TestUserBuilder;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
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
 * Проверка механизма валидации данных пользователя при регистрации.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDTOValidatorTest extends AbstractIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private ObjectMapper objectMapper;
    private UserEntity user;
    private final TypeReference<List<ErrorResponseDTO>> listType = new TypeReference<>() {
    };

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        user = new TestUserBuilder().build();
    }

    /**
     * Все данные верны
     */
    @Test
    void test_validationRegistration_allOk() throws JsonProcessingException {
        AuthDSL.register(user);
    }

    /**
     * Логин слишком короткий
     */
    @Test
    void test_validationRegistration_tooShortLogin() throws JsonProcessingException {
        user.setUsername("te");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Too short username")
                .codeError(ErrorCodes.INCORRECT_SIZE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Логин слишком длинный
     */
    @Test
    void test_validationRegistration_tooLongLogin() throws JsonProcessingException {
        user.setUsername("tetetetetetetetetetetete");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Too long username")
                .codeError(ErrorCodes.INCORRECT_SIZE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Логин пустой
     */
    @Test
    void test_validationRegistration_emptyLogin() throws JsonProcessingException {
        user.setUsername("             ");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(ErrorCodes.NOT_BLANK.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Вместо имени пользователя передаем null
     */
    @Test
    void test_validationRegistration_nullLogin() throws JsonProcessingException {
        user.setUsername(null);

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(ErrorCodes.NOT_BLANK.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Логин содержит недопустимые символы
     */
    @Test
    void test_validationRegistration_notAllowedSymbolsInLogin() throws JsonProcessingException {
        user.setUsername("vasya#");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Field can contains only letters, digits or underscore")
                .codeError(ErrorCodes.ONLY_LETTERS_DIGITS_UNDERSCORE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Логин должен содержать как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolLogin() throws JsonProcessingException {
        user.setUsername("234234234");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("This username must contains at least one character")
                .codeError(ErrorCodes.AT_LEAST_ONE_LETTER.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Пароль слишком короткий
     */
    @Test
    void test_validationRegistration_tooShortPassword() throws JsonProcessingException {
        user.setPassword("t2");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Too short password")
                .codeError(ErrorCodes.INCORRECT_SIZE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Вместо пароля передаем null
     */
    @Test
    void test_validationRegistration_nullPassword() throws JsonProcessingException {
        user.setPassword(null);

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Password should not be null")
                .codeError(ErrorCodes.NOT_NULL.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Пароль должен содержать как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolPassword() throws JsonProcessingException {
        user.setPassword("234234234");

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("This password must contains at least one character")
                .codeError(ErrorCodes.AT_LEAST_ONE_LETTER.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Название списка привычек слишком короткое
     */
    @Test
    void test_validationRegistration_tooShortHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("te"));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Too short habit list name")
                .codeError(ErrorCodes.INCORRECT_SIZE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Название списка привычек слишком длинное
     */
    @Test
    void test_validationRegistration_tooLongHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("tetetetetetetetetetetete"));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Too long habit list name")
                .codeError(ErrorCodes.INCORRECT_SIZE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals(expected, errors.get(0));
    }

    /**
     * Пустое название списка привычек
     */
    @Test
    void test_validationRegistration_emptyHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("             "));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(ErrorCodes.NOT_BLANK.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     *  Вместо названия списка привычек передаем null
     */
    @Test
    void test_validationRegistration_nullHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList(null));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("не должно быть пустым")
                .codeError(ErrorCodes.NOT_BLANK.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Название списка привычек содержит недопустимые символы
     */
    @Test
    void test_validationRegistration_notAllowedSymbolsInHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("vasya#"));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("Field can contains only letters, digits or underscore")
                .codeError(ErrorCodes.ONLY_LETTERS_DIGITS_UNDERSCORE.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }

    /**
     * Название списка привычек должно содержать как минимум один символ
     */
    @Test
    void test_validationRegistration_atLeastOneSymbolHabitListName() throws JsonProcessingException {
        user.setHabitList(new HabitList("234234234"));

        var result = AuthDSL.sendRegistrationRequest(user);

        var expected = new ErrorResponseDTO()
                .message("This habit list name must contains at least one character")
                .codeError(ErrorCodes.AT_LEAST_ONE_LETTER.ordinal());

        var errors = objectMapper.readValue(result, listType);

        Assertions.assertTrue(errors.contains(expected));
    }
}
