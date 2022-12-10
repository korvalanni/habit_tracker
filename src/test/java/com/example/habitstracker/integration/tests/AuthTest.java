package com.example.habitstracker.integration.tests;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.exceptions.ErrorCodes;
import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.auth.IncorrectCredentialsException;
import com.example.habitstracker.integration.utils.TestUserBuilder;
import com.example.habitstracker.integration.utils.dsl.DSLHelper;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.services.MapperService;
import com.example.openapi.dto.ErrorResponseDTO;
import com.example.openapi.dto.LoginPasswordDTO;
import com.example.openapi.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

/**
 * Тесты на механизм авторизации
 */
class AuthTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MapperService mapperService;
    private UserEntity user;

    @BeforeEach
    public void setup() {
        super.setup();
        user = new TestUserBuilder().build();
    }

    /**
     * Проверка механизма регистрации и логина
     */
    @Test
    void test_registrationAndLogin() throws JsonProcessingException {
        authDSL.register(user);
        authDSL.login(user);

        Assertions.assertNotNull(DSLHelper.getToken());
        Assertions.assertFalse(DSLHelper.getToken().isEmpty());
    }

    /**
     * Пробуем зарегистрировать одного и того же пользователя дважды
     */
    @Test
    void test_registerUserTwice() throws JsonProcessingException {
        authDSL.register(user);

        UserExistException exception = new UserExistException(user.getUsername());
        ErrorResponseDTO expected = new ErrorResponseDTO()
                .codeError(ErrorCodes.USER_EXISTS.getCode())
                .message(exception.getMessage());

        var dto = new UserDTO();
        mapperService.transform(user, dto);

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(ApiConstants.Auth.REGISTRATION)
                .getBody()
                .asString();
        // @formatter:on
        System.out.println(response);
        var result = objectMapper.readValue(response, ErrorResponseDTO.class);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Пробуем войти используя неверные данные для входа
     */
    @Test
    void test_incorrectPassword() throws JsonProcessingException, CloneNotSupportedException {
        authDSL.register(user);

        UserEntity newUser = (UserEntity) user.clone();
        newUser.setPassword("X");

        IncorrectCredentialsException exception = new IncorrectCredentialsException();
        ErrorResponseDTO expected = new ErrorResponseDTO()
                .codeError(ErrorCodes.INCORRECT_LOGIN_PASSWORD.getCode())
                .message(exception.getMessage());

        // @formatter:off
        LoginPasswordDTO dto = new LoginPasswordDTO();
        mapperService.transform(newUser, dto);

        String response = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(ApiConstants.Auth.LOGIN)
                .getBody()
                .asString();
        // @formatter:on

        ErrorResponseDTO result = objectMapper.readValue(response, ErrorResponseDTO.class);
        Assertions.assertEquals(expected, result);
    }
}