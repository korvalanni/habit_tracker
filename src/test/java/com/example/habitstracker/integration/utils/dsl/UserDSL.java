package com.example.habitstracker.integration.utils.dsl;

import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.models.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.habitstracker.integration.utils.dsl.DSLHelper.authorized;
import static io.restassured.RestAssured.given;

/**
 * Инструменты для взаимодействия с api пользователя
 */
@Component
public class UserDSL {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Удалить пользователя
     *
     * @param user Пользователь, которого удаляем
     */
    public void deleteUser(UserEntity user) {
        // @formatter:off
        authorized()
            .when()
                .post(ApiConstants.User.DELETE_USER, user.getUsername())
            .then()
                .statusCode(200);
        // @formatter:on
    }

    /**
     * Получить пользователя
     *
     * @param username Ник пользователя, которого ъотим получить
     */
    public void getUser(String username) {
        // @formatter:off
        authorized()
            .when()
                .post(ApiConstants.User.GET_USER, username)
            .then()
                .statusCode(200);
        // @formatter:on
    }

    // todo: надо разобраться с api

    /**
     * Обновить пользователя
     *
     * @param username Ник пользователя
     * @param user     Новые данные
     */
    public void updateUser(String username, UserEntity user) throws JsonProcessingException {
        // @formatter:off
       authorized()
               .contentType(ContentType.JSON)
               .body(objectMapper.writeValueAsString(user))
           .when()
               .put(ApiConstants.User.UPDATE_USER, username)
           .then()
               .statusCode(200);
        // @formatter:on
    }
}
