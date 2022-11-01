package com.example.habitstracker.dsl;

import com.example.habitstracker.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * Инструменты для взаимодействия с api пользователя
 */
public class UserDSL {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Удалить пользователя
     *
     * @param user Пользователь, которого удаляем
     */
    public static void deleteUser(User user) {
        // @formatter:off
        given()
                .headers("Authorization", "Bearer " + TokenHolder.token)
                .when()
                .post("/delete_user/" + user.getUsername())
                .then()
                .statusCode(200);
        // @formatter:on
    }

    /**
     * Получить пользователя
     *
     * @param username Ник пользователя, которого ъотим получить
     */
    public static void getUser(String username) {
        // @formatter:off
        given()
                .headers("Authorization", "Bearer " + TokenHolder.token)
                .when()
                .post("/get_user/" + username)
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
    public static void updateUser(String username, User user) throws JsonProcessingException {
        // @formatter:off
        given()
                .headers("Authorization", "Bearer " + TokenHolder.token)
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(user))
                .when()
                .post("/update_user/" + username)
                .then()
                .statusCode(200);
        // @formatter:on
    }
}
