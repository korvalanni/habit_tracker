package com.example.habitstracker.api;

import com.example.habitstracker.dsl.AuthDSL;
import com.example.habitstracker.dsl.TokenHolder;
import com.example.habitstracker.dto.ErrorResponseDTO;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.models.User;
import com.example.habitstracker.utils.DatabaseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;

/**
 * Тесты на механизм авторизации
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthTest {
    @LocalServerPort
    private Integer port;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        user = new User(0L, "Nik", "Cap", null);
    }

    @AfterEach
    void tearDown() {
        DatabaseUtils.clear(jdbcTemplate);
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
        var expected = new ErrorResponseDTO(1, exception.getMessage());

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(user))
                .when()
                .post("/auth/registration")
                .getBody()
                .asString();
        // @formatter:on

        var result = OBJECT_MAPPER.readValue(response, ErrorResponseDTO.class);
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
        var expected = new ErrorResponseDTO(1, exception.getMessage());

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(user))
                .when()
                .post("/auth/login")
                .getBody()
                .asString();
        // @formatter:on

        var result = OBJECT_MAPPER.readValue(response, ErrorResponseDTO.class);
        Assertions.assertEquals(expected, result);
    }
}
