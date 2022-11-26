package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.auth.IncorrectCredentialsException;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.ErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UserTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    private UserEntity user;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        super.setup();

        user = new TestUserBuilder().build();

        authDSL.register(user);
        authDSL.login(user);
    }

    /**
     * Проверка механизма смены пароля
     * <p>
     * 1) Регистрируемся
     * 2) Логинимся
     * 3) Обновляем пользователя (все, кроме пароля)
     * 4) Проверка, что логин со старым паролем работает
     * 5) Меняем пароль
     * 6) Проверяем, что логин по старому паролю не работает
     * 7) Проверяем, что логин по новому паролю работает
     */
    @Test
    void test() throws JsonProcessingException {
        UserEntity newUser = new TestUserBuilder().build();
        user.setUsername(newUser.getUsername());

        userDSL.updateUser(user);

        authDSL.login(user);

        String oldPassword = user.getPassword();
        user.setPassword(newUser.getPassword());

        userDSL.updatePassword(oldPassword, user.getPassword());

        var values = new HashMap<String, String>();
        values.put("username", user.getUsername());
        values.put("password", oldPassword);
        var json = objectMapper.writeValueAsString(values);

        var exception = new IncorrectCredentialsException();
        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(ApiConstants.Auth.LOGIN)
                .getBody()
                .asString();
        // @formatter:on
        var result = objectMapper.readValue(response, ErrorResponseDTO.class);

        var expected = new ErrorResponseDTO().message(exception.getMessage()).codeError(exception.getCode());

        Assertions.assertEquals(expected, result);

        authDSL.login(user);
    }
}
