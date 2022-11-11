package com.example.habitstracker.api;

import com.example.habitstracker.AbstractIntegrationTest;
import com.example.habitstracker.Constants;
import com.example.habitstracker.TestUserBuilder;
import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.dsl.TokenHolder;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

/**
 * Тесты на механизм авторизации
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthTest extends AbstractIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private ObjectMapper objectMapper;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        user = new TestUserBuilder().build();
    }

    /**
     * Проверка механизма регистрации и логина
     */
    @Test
    void test_registrationAndLogin() throws JsonProcessingException {
        AuthDSL.register(user);
        AuthDSL.login(user);

        Assertions.assertNotNull(TokenHolder.token);
        Assertions.assertFalse(TokenHolder.token.isEmpty());
    }

    /**
     * Пробуем зарегистрировать одного и того же пользователя дважды
     */
    @Test
    void test_registerUserTwice() throws JsonProcessingException {
        AuthDSL.register(user);

        var exception = new UserExistException(user.getUsername());
        var expected = new ErrorResponseDTO()
                .codeError(1)
                .message(exception.getMessage());

        var dto = UserMapper.toDTO(user);

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
            .when()
                .post("/auth/registration")
                .getBody()
                .asString();
        // @formatter:on

        var result = objectMapper.readValue(response, ErrorResponseDTO.class);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Пробуем войти используя неверные данные для входа
     */
    @Disabled("Надо доработать ответы на некорректные данные для авторизации")
    @Test
    void test_incorrectPassword() throws JsonProcessingException {
        AuthDSL.register(user);

        user.setPassword("X");

        var exception = new UserExistException(user.getUsername());
        final var expected = new ErrorResponseDTO()
                .codeError(1)
                .message(exception.getMessage());

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(user))
            .when()
                .post(ApiConstants.LOGIN)
                .getBody()
                .asString();
        // @formatter:on

        var result = objectMapper.readValue(response, ErrorResponseDTO.class);
        Assertions.assertEquals(expected, result);
    }
}
